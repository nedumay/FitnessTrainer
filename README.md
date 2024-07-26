# Fitness trainer <img src="https://img.shields.io/badge/version-1.1.0-green" alt="App Version">
An app for exercising at home. In development... Update: 26.07.2024

Application сapabilities ::
1. Registering/deleting and saving info about an account user (firebase).
2. Workout reminder by time and day.
3. Exercise list.
4. Exercise tracking (under development).

Currently in the works: Exercise tracking...

API in development (There are problems: the API often crashes, and I haven't found another option yet :) in the search ...): 

beginner: https://run.mocky.io/v3/4e4399ba-7eef-4fdc-b877-f457037e0fb9

continuing: https://run.mocky.io/v3/f5732aed-48f0-450c-a9f9-24bdb06c5b7a

advanced: https://run.mocky.io/v3/fbf0dbb0-57d1-4d43-bd56-c4e1f7fd4e29

# Development Environment

The app is written entirely in Kotlin and uses the Gradle build system.

Tools and Libraries: Firebase, Coroutines + StateFlow, Dagger2, Worker, YoutubePlayer, Glide, Navigation fragment.

Language Support: English, Russian

# Architecture

The architecture is built around Android Architecture Components and follows the recommendations laid out in the Guide to App Architecture. Logic is kept away from Activities and moved to ViewModels. Data is observed using Kotlin Flows and the View Binding Library binds UI components in layouts to the app's data sources. Dependency Injection is implemented with Dagger2.

![250057156-bcec8978-518c-41b1-9ee6-bcf2e77b3d47](https://github.com/nedumay/FitnessTrainer/assets/79632860/a631ad90-7e7e-40e2-8952-ea613b6a78cd)

# Login to the app
![Screenshot_20230630_124302](https://github.com/nedumay/FitnessTrainer/assets/79632860/f1a0f841-dc54-4432-aff6-2c243dbf8d8c)

# Registration
![Screenshot_20230630_124346](https://github.com/nedumay/FitnessTrainer/assets/79632860/613bf596-a87d-4419-a3a7-e7a229b561af)
![Screenshot_20230630_124356](https://github.com/nedumay/FitnessTrainer/assets/79632860/2cd47797-886f-486d-bce9-873e3bcb8337)
![Screenshot_20230630_124417](https://github.com/nedumay/FitnessTrainer/assets/79632860/5fa8d42f-89fe-4df9-bdeb-12047a3b83da)
![Screenshot_20230630_124449](https://github.com/nedumay/FitnessTrainer/assets/79632860/cf1631e2-d3d3-452b-88da-a9bbfec2f250)

# Forgot password
![Screenshot_20230630_124531](https://github.com/nedumay/FitnessTrainer/assets/79632860/bbd6e633-58c0-4b1a-b349-70093a940f5f)

# Main
![Screenshot_20230630_124801](https://github.com/nedumay/FitnessTrainer/assets/79632860/087ae97e-ed9e-4434-b5dd-b92b15cf417c)

# Notification
![Screenshot_20230630_124733](https://github.com/nedumay/FitnessTrainer/assets/79632860/fe11b978-7506-4b38-9be2-154639b66149)
![Screenshot_20230630_124747](https://github.com/nedumay/FitnessTrainer/assets/79632860/3c433a7e-592a-4ce5-a750-6c4956fbdd52)

# Exercise
![Screenshot_20230630_124633](https://github.com/nedumay/FitnessTrainer/assets/79632860/a1408255-4c96-4eb4-be04-6877c9e227ed)
![Screenshot_20230630_124644](https://github.com/nedumay/FitnessTrainer/assets/79632860/ced168d9-1216-411f-8f46-3a2d6db4d854)
![Screenshot_20230630_124702](https://github.com/nedumay/FitnessTrainer/assets/79632860/896bcfa5-ff1f-4301-9845-f2bc5f530990)

# Settings
![Screenshot_20230630_124557](https://github.com/nedumay/FitnessTrainer/assets/79632860/a8407363-6e6a-4f49-bef3-f0301a1fbf4c)
