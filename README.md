# 📝 ToDoApp - Jetpack Compose Notes App

Una aplicación de notas simple y moderna desarrollada con **Kotlin** y **Jetpack Compose**, que permite agregar, editar, eliminar, buscar y ordenar notas, todo con un diseño limpio y soporte para tema claro/oscuro.

---

## 📸 Capturas de Pantalla

<p float="left">
  <img src="https://github.com/user-attachments/assets/ee0bc65a-87aa-48a7-a2e4-025dcea19d4f" width="200"/>
  <img src="https://github.com/user-attachments/assets/76edf5b9-dd8a-4777-9a56-ed7691f97e8f" width="200"/>
  <img src="https://github.com/user-attachments/assets/94c01505-2fad-4b50-9c48-104a77f1b32b" width="200"/>
</p>

---

## 🚀 Características

- ✅ Crear y editar notas de texto.
- 🗑️ Eliminar notas existentes.
- 🔎 Buscar notas por título o contenido.
- ↕️ Ordenar notas por:
  - Color
  - ID (usado como fecha de creación)
  - Orden alfabético
- 🌗 Soporte para tema claro y oscuro (cambio manual).
- ⚡ Flujo de estado reactivo usando `StateFlow`.
- Clean architecture MVVM

---

## 🛠 Tecnologías utilizadas

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - Persistencia local
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

---

## ⚙️ Instalación y ejecución

Sigue estos pasos para correr la app localmente:

1. Clona este repositorio:
    ```bash
    git clone https://github.com/Mictlanti/ToDoAppCompose.git
    cd ToDoApp
    ```

2. Abre el proyecto en **Android Studio**.

3. Conéctate a un emulador o dispositivo físico.

4. Haz clic en el botón **Run ▶️** o presiona `Shift + F10`.

---

## 📌 Notas adicionales

- El orden por "fecha de creación" se basa en el `id` autogenerado por Room.
- La búsqueda es insensible a mayúsculas/minúsculas y se aplica tanto al título como al cuerpo de la nota.

---

## 🧑‍💻 Autor

**Daniel Rosas**  
[GitHub](https://github.com/Mictlanti)  
*Gracias por probar mi app. ¡Cualquier sugerencia es bienvenida!*

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más información.

