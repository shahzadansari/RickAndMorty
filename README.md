# Rick & Morty App ✨

This project demonstrates **Clean Architecure** best practices in a **Multi-Module** Android application using **MVI** architecture pattern. This app is made entirely in Kotlin and consumes pure Kotlin libraries to manage application data **(SqlDelight, Ktor)** and UI **(Jetpack Compose)**.

## Screenshots (Light & Dark Color Schemes)

<img width="345" alt="characters-list" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/46920ca2-7616-4cb6-a6a5-01886b816c50">

<img width="345" alt="character-details" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/e5356755-af20-4a40-9550-1cee56950810">

<img width="345" alt="characters-list-dark" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/94145aa5-e2f2-4fd5-96b4-b45dda8521a0">

<img width="345" alt="characters-details-dark" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/3021ee4e-ae05-4e0f-bad4-a74dea5d1029">



## Dynamic Colors - Android 12+
Dynamic Color enables users to personalize their devices to align tonally with the color scheme of their personal wallpaper or through a selected color in the wallpaper picker. You can read more about Dynamic Colors [here](https://developer.android.com/develop/ui/views/theming/dynamic-colors).

https://github.com/shahzadansari/RickAndMorty/assets/43310446/1ce325e6-265e-4a7a-bb0a-f6e1ff5c36eb

## Architecture Overview

The app architecture has three layers: a [data layer](https://developer.android.com/jetpack/guide/data-layer), a [domain layer](https://developer.android.com/jetpack/guide/domain-layer) and a [UI layer](https://developer.android.com/jetpack/guide/ui-layer). The architecture follows a reactive programming model with [unidirectional data flow](https://developer.android.com/jetpack/guide/ui-layer#udf). With the data layer at the bottom, the key concepts are:
*   Higher layers react to changes in lower layers.
*   Events flow down.
*   Data flows up.

The data flow is achieved using streams, implemented using [Kotlin Flows](https://developer.android.com/kotlin/flow).

<img width="530" alt="architecture-overview" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/4a1c529b-450e-45e0-ab6b-ee434e925a96">

### UI Layer
The UI layer is the pipeline that converts application data changes to a form that the UI can present and then displays it. The state is managed using a [Unidirectional Data Flow (UDF)](https://developer.android.com/topic/architecture/ui-layer#udf) which aligns with the  **MVI (Model View Intent)** architecture pattern's **event-based** nature.

<img width="530" alt="ui-layer-arch" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/f3a89ee9-e3af-4077-9f94-73cc12486f0d">

### Data Layer
While the UI layer contains UI-related state and UI logic, the data layer contains application data and business logic. The business logic is what gives value to your app—it's made of real-world business rules that determine how application data must be created, stored, and changed. This separation of concerns allows the data layer to be used on multiple screens, share information between different parts of the app, and reproduce business logic outside of the UI for unit testing.

<img width="667" alt="data-layer-arch" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/3b64e4cc-3d6f-4f37-ba8b-a9ef534bc406">

### Domain Layer
The domain layer is an optional layer that sits between the UI layer and the data layer. The domain layer is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels. This layer contains Usecases/Interactors which interact with data sources inside Data Layer.

<img width="540" alt="domain-layer-arch" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/d8db0c33-1d32-4508-b1f6-129046c7095e">

---

This app has two screens right now:
1. **Characters List Screen**
2. **Character Details Screen**

## Characters List Screen Architecture

<img width="530" alt="characters-list-arch" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/9cdd1ee9-9d0d-4f56-a593-04abc2be858e">

## Character Details Screen Architecture

<img width="530" alt="character-details-arch" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/f2d66f2b-e4f9-4f4a-bef0-2032e517c705">

## Modularization Overview

<img width="528" alt="modulairzation-overview" src="https://github.com/shahzadansari/RickAndMorty/assets/43310446/852ed873-4a66-48b2-b1d0-c95c3212da16">
