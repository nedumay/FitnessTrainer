# Fitness trainer <img src="https://img.shields.io/badge/version-1.1.0-green" alt="App Version">
An app for exercising at home. In development... Update: 16.05.2023

Application сapabilities::
1. Registering/deleting account user (firebase).
2. Workout reminder (under development).
3. Exercise list and tracking (under development).

API in development: https://run.mocky.io/v3/5739db8d-eb9b-456b-a8fd-9d9707fbf698 

# Development Environment

The app is written entirely in Kotlin and uses the Gradle build system.

Tools and Libraries: Firebase, Coroutines + Flow, Dagger2

Language Support: English, Russian

# Architecture

The architecture is built around Android Architecture Components and follows the recommendations laid out in the Guide to App Architecture. Logic is kept away from Activities and moved to ViewModels. Data is observed using Kotlin Flows and the View Binding Library binds UI components in layouts to the app's data sources. Dependency Injection is implemented with Dagger2.

![12](https://user-images.githubusercontent.com/79632860/228531613-994ba3e1-0eef-4b16-b263-09d722285428.png)

# Login to the app
![login](https://user-images.githubusercontent.com/79632860/228531981-c7876a15-dc7c-42d8-8771-8f0bd7d3cd8f.png)

# Registration activity
![4](https://user-images.githubusercontent.com/79632860/187088467-1576b5c1-3603-46be-8709-5d8a9780e688.png)
![4 on](https://user-images.githubusercontent.com/79632860/187088473-0a948fe2-fba1-49ab-af31-03abe3935649.png)
![4 ft](https://user-images.githubusercontent.com/79632860/187088474-1e533cf0-24ed-4a8d-a2fe-232044bc6b8c.png)
![24](https://user-images.githubusercontent.com/79632860/228532530-64e551ec-d215-40f0-b393-9f91c37efbfc.png)

# Mains activity
![Dashboard](https://user-images.githubusercontent.com/79632860/187088516-9937e4c9-f07f-45d4-929b-fe0f2d021cfd.png)
![Notification](https://user-images.githubusercontent.com/79632860/187088519-9144558f-d76c-4685-9d72-8f022fc0624e.png)

# Settings activity
![Settings man](https://user-images.githubusercontent.com/79632860/187088530-a88463c1-630a-4f7c-a870-38cdf4909326.png)
