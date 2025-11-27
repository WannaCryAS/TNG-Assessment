This repo is to show list of user fetched from https://jsonplaceholder.typicode.com/users and also fetch avatar from https://ui-avatars.com/

Android:
Jetpack Compose
MVVM + Clean Architechture
Koin
Coil (AsyncImage - compose)

```bash
.
├── MainActivity.kt
├── MyApp.kt
├── config
│   ├── Constants.kt
├── di
│   └── AppModule.kt
├── domain
│   ├── data
│   │   └── User.kt
│   ├── repo
│   │   ├── UserRepository.kt
│   │   └── UserRepositoryImpl.kt
│   ├── service
│   │   └── UserServiceApi.kt
│   └── usecase
│       └── GetUserUseCase.kt
├── presentation
│   ├── UiState.kt
│   ├── screen
│   │   ├── UsersScreen.kt
│   │   └── component
│   └── viewmodel
│       └── UsersViewModel.kt
└── ui
    └── theme
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt

```

        

