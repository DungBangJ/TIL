- [Language](#language)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [src](#src)
- [Running the application](#running-the-application)

---

## Language
- TypeScript
- Node.js

## Prerequisites
- Node.js

## Setup

```zsh
$ npm i -g @nestjs/cli
$ nest new project-name
```

## src

- app.controller.ts
  - A basic controller with a single route.

> route
> 
> a mapping between a URL path and a function that should be executed when that path is requested

- app.controller.spec.ts
  - the unit tests for the controller

- app.module.ts
  - the root module of a application

> module
>
> It is responsible for setting up the entire application and configuring the dependencies, providers, controllers, and other features of the application.

- app.service.ts
  - a basic service with a single method.

- main.ts
  - the entry file of the application which uses the core function `NestFactory` to create a nest application instance.

> NestFactory
>
>  NestFactory provides a convenient way to **create and configure a Nest application instance**, making it easy to get started with building web application in NestJS. NestFactory takes a module(typically the root module of the application) as an argument and returns an instance of the `INestApplication` interface, which represents the running Nest application.

main.ts
```javascript
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);
}
bootstrap();
```
- create()
  - returns an application object
  - fulfills the `INestApplication` interface.


## Running the application

```zsh
$ npm run start
```

- this command starts the app with the HTTP server listening on the port defined in the `src/main.ts` file.

```zsh
$ npm run start:dev
```

- with this command, you will watch your files, automatically recompiling and reloading the server.
