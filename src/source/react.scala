import djinni.{JavaMarshal, ObjcMarshal, ObjcppMarshal}
import djinni.ast.Interface.Method
import djinni.ast._
import djinni.generatorTools.ObjcIdentStyle
import djinni.syntax.Error

package object react {
  val CONSTRUCTOR_NAME = "create"
  private val TYPES_CLASS_NAME = "ReactBridge"
  private val CALLBACK_CLASS_NAME = "JavascriptCallback"
  private val PROMISE_CLASS_NAME = "JavascriptPromise"

  def isConstructor(i: Interface, m: Method) = (i.ext.react && m.ident.name == CONSTRUCTOR_NAME)

  val reactUtilityClasses = Seq(TYPES_CLASS_NAME, "JavascriptType", "JavascriptMapKeyIterator")
  val primitiveTypes = Seq("bool", "i32", "f64", "string")
  var allowedTypes: Seq[String] = null
  var reactTypes = Map[String, TypeDecl]()

  def process(idl: Seq[TypeDecl]) = {
    reactTypes = idl.filter(typeDef => {
      typeDef match {
        case et: ExternTypeDecl => if (et.origin == "react.yaml") true else false
        case _ => false
      }
    }).map(typeDef => typeDef.ident.name -> typeDef).toMap
    allowedTypes = primitiveTypes ++ reactTypes.keySet.diff(reactUtilityClasses.toSet)

    idl.map(typeDef => {
      typeDef match {
        case it: InternTypeDecl => {
          it.body match {
            case i: Interface => if (i.ext.react) {
              it.copy(body = i.copy(methods = createReactConstructor(it, i, reactTypes(TYPES_CLASS_NAME).ident) +: i.methods))
            } else {
              it
            }
            case _ => it
          }
        }
        case _ => typeDef
      }
    })
  }

  def isAllowedType(typeRef: Option[TypeRef]) = {
    typeRef.isDefined && allowedTypes.contains(typeRef.get.expr.ident.name)
  }

  def isReactType(typeRef: Option[TypeRef]) = {
    typeRef.isDefined && reactTypes.contains(typeRef.get.expr.ident.name) && !reactUtilityClasses.contains(typeRef.get.expr.ident.name)
  }

  def isPrimitive(typeRef: Option[TypeRef]) = {
    typeRef.isDefined && primitiveTypes.contains(typeRef.get.expr.ident.name)
  }

  private def validateMethodParamTypes(m: Method) = {
    if (!m.ret.isEmpty) {
      throw Error(m.ident.loc, "Methods can return only void for +r interfaces").toException
    }

    for ((p, i) <- m.params.zipWithIndex) {
      if (!isAllowedType(Some(p.ty))) {
        throw Error(p.ident.loc, "Only primitive or react types are allowed as parameters: " + allowedTypes.mkString(", ")).toException
      }

      if (i < m.params.size - 1 && p.ty.expr.ident.name == PROMISE_CLASS_NAME) {
        throw Error(p.ident.loc, "Promises are allowed only as a last parameter").toException
      }
    }
  }

  def validateInterface(i: Interface) = {
    for (m <- i.methods) {
      if (!react.isConstructor(i, m)) {
        if (m.static)
          throw Error(m.ident.loc, "static not allowed for +r interfaces").toException
        if (m.const)
          throw Error(m.ident.loc, "const method not allowed for +r interfaces").toException
        validateMethodParamTypes(m)
      }
    }

    for (c <- i.consts) {
      if (!isPrimitive(Some(c.ty))) {
        throw Error(c.ident.loc, "Only primitive types are allowed for +r interfaces: " + primitiveTypes.mkString(", ")).toException
      }
    }
  }

  val javaParamTypeToReact = Map(
    "JavascriptMap" -> "ReadableMap",
    "JavascriptArray" -> "ReadableArray",
    "JavascriptPromise" -> "Promise",
    "JavascriptCallback" -> "Callback"
  )

  val objcParamTypeToReact = Map(
    "JavascriptMap" -> "nullable NSDictionary *",
    "JavascriptArray" -> "nullable NSArray *",
    "JavascriptPromise" -> Map("resolver" -> "nonnull RCTPromiseResolveBlock", "rejecter" -> "nonnull RCTPromiseRejectBlock"),
    "JavascriptCallback" -> "nonnull RCTResponseSenderBlock",
    // Fixes for primitves, f64 and bool are fine
    "i32" -> "int",
    "string" -> "nullable NSString *"
  )

  val objcParamReactWrapperCalls = Map(
    "JavascriptMap" -> Left("wrapMap"),
    "JavascriptArray" -> Left("wrapArray"),
    "JavascriptPromise" -> Right("wrapPromiseWithResolver:resolver rejecter:rejecter"),
    "JavascriptCallback" -> Left("wrapCallback")
  )

  def mapJavaParamType(typeRef: TypeRef, marshaller: JavaMarshal) = {
    if (isReactType(Some(typeRef))) {
      javaParamTypeToReact(typeRef.expr.ident.name)
    } else if (isPrimitive(Some(typeRef))) {
      marshaller.paramType(typeRef)
    } else {
      throw Error(typeRef.expr.ident.loc, "Invalid parameter type").toException
    }
  }

  def createObjcMethodDeclarationParamWriter(idObjc: ObjcIdentStyle, marshal: ObjcMarshal): ((Field) => Seq[(String, String)]) = {
    (p: Field) => {
      val name = idObjc.field(p.ident.name)
      val paramTypeDjinni = p.ty.expr.ident.name
      objcParamTypeToReact.getOrElse(paramTypeDjinni, marshal.paramType(p.ty)) match {
        case paramType: String => Seq((name, s"(${paramType})${idObjc.local(p.ident.name)}"))
        case m: Map[String, String] @unchecked => m.map {
          case (paramLabel: String, paramType: String) => (paramLabel, s"(${paramType})${paramLabel}")
        }.toSeq
      }
    }
  }

  def createObjcMethodParamDefinitionWriter(idObjc: ObjcIdentStyle, marshal: ObjcMarshal): ((Field) => Seq[(String, String)]) = {
    (p: Field) => {
      val name = idObjc.field(p.ident.name)
      val paramTypeDjinni = p.ty.expr.ident.name;
      objcParamTypeToReact.getOrElse(paramTypeDjinni, marshal.paramType(p.ty)) match {
        case paramType: String => Seq((name, s"(${paramType})${idObjc.local(p.ident.name)}"))
        case m: Map[String, String] @unchecked => m.map {
          case (paramLabel: String, paramType: String) => (paramLabel, s"(${paramType})${paramLabel}")
        }.toSeq
      }
    }
  }

  def createObjcppMethodDeclarationWriter(idObjc: ObjcIdentStyle, marshal: ObjcMarshal): ((Method) => (String, String)) = {
    (method: Interface.Method) => {
      val methodName = idObjc.method(method.ident.name)
      if (methodName == CONSTRUCTOR_NAME) {
        val label = if (method.static) "+" else "-"
        val ret = marshal.returnType(method.ret)
        (s"$label ($ret)${methodName}", "")
      } else {
        method.params.lastOption match {
          case Some(field) => field.ty.expr.ident.name match {
            case PROMISE_CLASS_NAME => (s"RCT_REMAP_METHOD(${methodName}, $methodName", ")")
            case CALLBACK_CLASS_NAME | _ => (s"RCT_EXPORT_METHOD(${methodName}", ")")
          }
          case None => (s"RCT_EXPORT_METHOD(${methodName}", ")")
        }
      }
    }
  }

  def createObjcppMethodParamDefinitionWriter(idObjc: ObjcIdentStyle, marshal: ObjcppMarshal): ((Field) => String) = {
    (p: Field) => {
      val name = idObjc.local(p.ident.name)
      val paramTypeDjinni = p.ty.expr.ident.name;
      val call = objcParamReactWrapperCalls.get(paramTypeDjinni) match {
        case Some(Right(wrapperLiteralCall: String)) => s"[RVReactDjinni ${wrapperLiteralCall}]"
        case Some(Left(wrapperSimpleCall: String)) => s"[RVReactDjinni ${wrapperSimpleCall}:${name}]"
        case _ => name
      }
      marshal.toCpp(p.ty, call)
    }
  }

  private def createReactConstructor(it: InternTypeDecl, i: Interface, javascriptTypesIdent: Ident): Method = {
    val methodIdent = Ident(CONSTRUCTOR_NAME, it.ident.file, it.ident.loc)
    val paramIdent = Ident("bridge", it.ident.file, it.ident.loc)
    val paramType = TypeRef(TypeExpr(javascriptTypesIdent, Seq()))
    val params = Seq(Field(paramIdent, paramType, Doc(Seq())))
    val ret = Some(TypeRef(TypeExpr(it.ident, Seq())))
    Method(methodIdent, params, ret, Doc(Seq()), true, false)
  }
}