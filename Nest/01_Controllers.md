## Controllers

Handles incoming requests and returning responses to the client.

purpose
- receive specific requests for the application.

to create a basic controller
- use classes and decorators

decorators
- enable Nest to create a routing map
  - tie requests to the corresponding controllers

## Routing

@Controller()
- is required to define a basic controller

```javascript
import { Controller, Get } from '@nestjs/common';

@Controller('cats')
export class CatsController {

  @Get()
  findAll(): string {
    return 'This action returns all cats';
  }
}
```

- use path prefix in a `@Controller()`
  - `cats` -> `/cats`
  - minimize repetitive code
- @Get()
  - HTTP request method decorator
  - tells Nest to create a handler for a specific endpoint for HTTP requests.
  - Nest will map `GET /cats` requests to the handler.
  - if `@Get('breed')`
    - `GET /cats/breed`
- return
  - 200 status
  - response
    - string

