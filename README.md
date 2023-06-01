# Fitness trainer <img src="https://img.shields.io/badge/version-1.1.0-green" alt="App Version">
An app for exercising at home. In development... Update: 02.06.2023

Application сapabilities::
1. Registering/deleting and saving info about an account user (firebase).
2. Workout reminder (under development).
3. Exercise list.
4. Exercise tracking (under development).

API in development: 

beginner: https://run.mocky.io/v3/d8c1a02d-cb90-43ee-bf93-d07d915f41fa

continuing: https://run.mocky.io/v3/9454d6a3-cc2a-49a1-92f3-bb785af3a27d

advanced: https://run.mocky.io/v3/eee2b1ce-1fd7-4b4c-8156-eda8a4ea16d7

# Development Environment

The app is written entirely in Kotlin and uses the Gradle build system.

Tools and Libraries: Firebase, Coroutines + StateFlow, Dagger2

Language Support: English, Russian

# Architecture

The architecture is built around Android Architecture Components and follows the recommendations laid out in the Guide to App Architecture. Logic is kept away from Activities and moved to ViewModels. Data is observed using Kotlin Flows and the View Binding Library binds UI components in layouts to the app's data sources. Dependency Injection is implemented with Dagger2.

![1](https://github.com/nedumay/FitnessTrainer/assets/79632860/dc7fed12-8ef9-4837-9e33-9239a0b4832b)

# Login to the app
![3](https://github.com/nedumay/FitnessTrainer/assets/79632860/77cb1b9f-d675-4520-b95f-057c3200322f)

# Registration activity
![5](https://github.com/nedumay/FitnessTrainer/assets/79632860/2212daf8-3d9f-43d9-8a2a-71319ac1ce67)
![6](https://github.com/nedumay/FitnessTrainer/assets/79632860/00424369-0dd8-443f-987a-a87d79e23cda)
![12](https://github.com/nedumay/FitnessTrainer/assets/79632860/2c756d2b-7f39-4283-997c-e6797280066e)
![7](https://github.com/nedumay/FitnessTrainer/assets/79632860/e9b1712e-ab55-40f3-a6cc-97f255720a33)

# Forgot password activity
![4](https://github.com/nedumay/FitnessTrainer/assets/79632860/824b90d6-9968-4db5-935e-68e0bde2093e)

# Main activity
![2](https://github.com/nedumay/FitnessTrainer/assets/79632860/6014d0d6-df90-4ca8-9b1f-a46d0217d2b5)

# Exercise activity
![9](https://github.com/nedumay/FitnessTrainer/assets/79632860/24b2f870-6799-4f5d-9c07-d1b52166501d)
![10](https://github.com/nedumay/FitnessTrainer/assets/79632860/c11d1567-e731-4415-a666-89e108f46ca5)
![11](https://github.com/nedumay/FitnessTrainer/assets/79632860/6fb8de3d-da8c-481b-b5bc-e06b2f3e16b9)

# Settings activity
![8](https://github.com/nedumay/FitnessTrainer/assets/79632860/3c4ca4b0-13e0-4358-b71b-4df9eeb9fc31)

