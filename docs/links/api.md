# Open API documentation

In our project we use OpenAPI Specification (formerly Swagger Specification) as an API description format for REST APIs. 
An OpenAPI file describes entire API, including:
* Available endpoints (/users) and operations on each endpoint (GET /users, POST /users)
* Operation parameters Input and output for each operation
* Authentication methods
* Contact information, license, terms of use and other information. </br>

The code contains OpenAPI annotations and Swagger configuration file which are used to generate API description.
To get it in JSON format file just start the application locally and visit http://localhost:8080/v2/api-docs. 
The file you obtain can be used to open with [Online Swagger Editor] (editor.swagger.io) command File -- Import File.

Another option to get access to the API documentation is Swagger UI, which is automatically generated 
after each run of your local project copy at http://localhost:8080/swagger-ui.html. 

## Annotations

It's recommended to use interfaces (implemented in controllers) and object models properly annotated. 
Detailed description for annotations to declare and manipulate Swagger output is located here: https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X. 
 
### Resource API Declaration
The @Api is used to apply definitions for all the operations defined under it.
It can also be used to declare authorization at the resource-level. 
These definitions apply to all operations under this resource, but can be overridden at the operation level if needed. 
```
@Api 
@RequestMapping(value = "/v1/links")
public interface LinksControllerApi {
```

### Operation Declaration
The @ApiOperation is used to declare a single operation. 
An operation is considered a unique combination of a path and a HTTP method.
```
@ApiOperation(value = "Save a link", notes = "Adds a new link to the links list.", response = Link.class, tags={  })
```

It's a common practice to return errors (or other success messages) using HTTP status codes. 
The @ApiResponse describes a concrete possible response. 
It cannot be used directly on the method or class/interface and needs to be included in the array value of @ApiResponses (whether there's one response or more).
```
@ApiResponses(value = {
    @ApiResponse(code = 201, message = "Link successfully has been saved.", response = Link.class),
    @ApiResponse(code = 400, message = "Link has not been saved.", response = Void.class),
    @ApiResponse(code = 500, message = "Unexpected error.", response = Void.class) })
```

@Authorization, @AuthorizationScope are used as input to @Api and @ApiOperation only, and not directly on the resources and operations. 
Once you've declared and configured which authorization schemes you support in your API, you can use these annotation to note which authorization scheme is required on a resource or a specific operation.

### Model Declaration
Swagger-core builds the model definitions based on the references to them throughout the API introspection. 
The @ApiModel allows you to manipulate the meta data of a model from a simple description or name change to a definition of polymorphism.
At its basic functionality, you can use @ApiModel to change the name of the model and add a description to it:

```
@ApiModel(value="DifferentModel", description="Sample model for the documentation")
class OriginalModel {...}
```

The @ApiModelProperty allows controlling Swagger-specific definitions such as allowed values, and additional notes. It also offers additional filtering properties in case you want to hide the property in certain scenarios.
```
@ApiModelProperty(value = "pet status in the store", allowableValues = "available,pending,sold")
public String getStatus() {
    return status;
}
```

### Swagger Definition

The SwaggerDefinition annotation provides properties corresponding to many (but not all) top-level properties of the Swagger object, allowing you to set these for your auto-generated definition.