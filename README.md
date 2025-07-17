# De Views a Jetpack Compose: Evolución de la Interfaz de Usuario en Android

**Repositorio original:** [Weris original](https://github.com/esuarezlo/Weris/tree/master)  

**Repositorio migrado:** [Weris SustiDANP](https://github.com/nelsonhuaman/Weris-SustiDANP.git)

**Enlace Video:** [Video](https://drive.google.com/file/d/1Z2_NInL5jZ6GMYLr84baYtvvFBTwpW_U/view?usp=drive_link)

---

## ✅ Objetivo

Transformar la arquitectura de la app original basada en **Fragments y XML (Views)** a una arquitectura moderna utilizando **Jetpack Compose**, **MVVM** e **inyección de dependencias con Hilt**, mejorando la **mantenibilidad**, **escalabilidad** y **experiencia del desarrollador**.

---
## 📱 Descripción del Proyecto

**Weris** es una aplicación móvil para Android que funciona como una plataforma de servicios locales con integración de geolocalización y características de red social. Permite a los usuarios:

- Descubrir servicios locales mediante un mapa interactivo (Google Maps)
- Visualizar publicaciones (posts)
- Buscar servicios por categoría y ubicación
- Usar la cámara para capturar contenido
---

## 🔍 Estado del Proyecto Original

Antes de la migración, la app tenía implementadas:

### Pantallas y Fragments:

- `MainActivity`: Pantalla de login básica
- `HomeActivity`: Pantalla principal con Bottom Navigation
- `ProfileActivity`: Pantalla de perfil de usuario
- `MapFragment`, `PostFragment`, `PostDetailFragment`, `ServiceFragment`
- 🚧 `CameraFragment` y `OneInfoFragment`: parcialmente funcionales

### Lógica parcialmente funcional:

- Navegación básica entre Activities y Fragments
- Google Maps integrado
- Adaptadores de lista (`PostAdapter`, `ServiceAdapter`)
- Sistema de red usando `AsyncTask` (obsoleto)
- Autenticación solo visual (sin backend real)

### Problemas detectados:

- Código duplicado y comentado sin limpiar
- Fragmentos mal renderizados (incompatibilidad con AndroidX)
- Dependencias `support` mezcladas con `androidx`
- Uso de `AsyncTask` (deprecated)
- Falta de arquitectura definida (sin patrón como MVVM)
- Networking inseguro (URLs hardcoded con HTTP)

---

## 🔁 Proceso de Migración

El objetivo fue migrar la arquitectura tradicional a un enfoque **moderno con Jetpack Compose y MVVM**.

### Actividades realizadas:

- Estructuración inicial del proyecto con arquitectura **MVVM**
- Implementación de **Hilt** para inyección de dependencias
- Conversión de `Fragments` a `Composables`
- Uso de **StateFlow** y `remember` para manejo de estados reactivos
- Navegación con **Navigation Compose**
- Reescritura de pantallas: Login, Post, Detalle, Perfil, Mapa, Servicios, Cámara, WebView

---

## 🧱 Arquitectura y Tecnologías

| Componente    | Tecnología                       |
|---------------|----------------------------------|
| UI            | Jetpack Compose + Material 3     |
| Arquitectura  | MVVM + Repository Pattern        |
| DI            | Hilt                             |
| Networking    | Retrofit                         |
| Navegación    | Navigation Compose               |
| Mapas         | Google Maps Compose              |
| Cámara        | CameraX + Compose (parcial)      |

---

## 📸 Funcionalidad de Cámara (en progreso)

- Botón “Tomar foto” redirige a pantalla para captura
- Botón “Ver galería” muestra fotos guardadas localmente

---

## ⚠️ Principales Dificultades Encontradas

- Fragmentación del código en el proyecto original
- Incompatibilidades de versiones y dependencias legacy
- Cámara sin una lógica clara ni layout funcional
- Uso de `AsyncTask` deprecated y sin HTTPS en URLs

---

## ✅ Recomendaciones Futuras

- Terminar la migración del sistema de red usando Retrofit + Coroutines
- Finalizar la funcionalidad de la cámara y galería
- Integrar autenticación segura y almacenamiento persistente
- Aplicar pruebas unitarias y de integración

---

## 🧪 Comparación entre enfoques

| Aspecto         | Antes (XML + Fragments) | Ahora (Jetpack Compose) |
|------------------|--------------------------|--------------------------|
| Declaración UI   | Imperativa               | Declarativa              |
| Velocidad Dev    | Lenta                    | Rápida (Hot Reload)      |
| Mantenimiento    | Propenso a errores       | Modular y reutilizable   |
| Manejo de estado | Manual                   | Reactivo (StateFlow)     |
| Arquitectura     | Ninguna                  | MVVM + Hilt              |

---

## 🎯 Equipo de Migración

- Nelson Aluyis Huaman Apaza
- Bryan Orlando Hancco Condori
