# OpenBankTest

**OpenBankTest** is a sample baseline project based on the Plexus Tech Android prototype on which aims to show a standard state-of-the-art proposal for Android development.

## Architecture and project organization
To address this sample app development, the team has decided to employ a class hierarchy based on the [**Clean Architecture**](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) paradigm, a concept with an increasing popularity thanks to Robert C. Martin (Uncle Bob).

<img src="docs/images/clean-architecture-cejas-1.png" width="300">

Therefore, the prior idea behind **OpenBankTest** is concern-layers separation. Each of this entities is in charge of certain responsibilities, which are handled in isolation. These layers get interconnected thanks through interfaces, which allow to achieve the necessary abstraction between them.

* **Presentation**
This layer's duties consist of managing the events caused by the user interactions, and rendering the information coming from the _domain_ layer. In this particular case, I has opted for using the Model-View-ViewModel architecture pattern. This entity "sees" the _domain_ layer.
Interested things are:
   - StateFlow for view that observes states modifications from viewModel.
   - Launch coroutines from viewModel with viewModelScope, so can automatically cancel the coroutine. The Dispatcher will be the Main, so in case we need to change it, we can do it later (for example, in the dataSource).
   - ViewModel ask data across domain with Controller.
   - I have created the views in a simple way, so I don't have spend time on styles and make pretty views.

* **Domain**
This layer is in charge of the application business logic. It is built upon _use-cases_ and repositories (_repository pattern_). The _domain_ layer obtains data from the _data_ module, use them to perform all the required operations, and format the outcomes to later deliver them to the _presentation_ layer. This entity only contains Kotlin code, and thus testing should only consist of **Unit Tests**. This layer represents the most inner entity, and thus it does not "see" anyone but itself.

* **Data**
This layer simply contains libraries and frameworks which provide data to the application (data sources). Among them, stand out service-query frameworks (_Retrofit_), local databases (_Room_), events tracking (_Omniture_), etc. This layer "sees" the _domain_ layer.
I use 2 interceptors: - RequestKeyInterceptor for handler keys authorizations on request. Regarding the [Marvel API](https://developer.marvel.com/documentation/generalinfo), a user api public and private key is required. For this particular sample app, a default key has been automatically configured. This sensitive information should be placed in a separate file, and excluded from any VCS scheme.
- ConnectivityInterceptor for handler connectivity and throws a custom exception.

Request are handled on `safeCall` extension that manage Failures and receives the mapper to transform Dto object.``

The usage of this class hierarchy and package organization pursues grasping the **SOLID** principles, getting more flexible when implementing new functionality, and easing code testing.

### Inversion of Control
In order to facilitate the interaction between the above described layers, **OpenBankTest** uses a service locator. **[Koin](https://www.raywenderlich.com/9457-dependency-injection-with-koin)** is a framework which allows to abstract type injection in a neat and clear manner.

### Arrow
`Either` data type is used, allowing to parametrize any data source query available in an simple way.

### Handling errors
Error handling is very important, as well as showing the user what to do in case of an error. For that, I handle all kind of errors into Failure for each layer. Each layer is responsible for handle the failure to model it on whatever is needed. I write the most usual errors, but if there is one more specific, you can create a FeatureFailure.

### Testing
There are many kinds of tests that we can include, but as this app is a test, I will only make a few of Unit and UI test. The ideal is to cover all, even mappers and any type of logic in the app.
