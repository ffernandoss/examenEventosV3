https://github.com/ffernandoss/examenEventosV3.git


# Examen Eventos V3

## Descripción del Proyecto

Este proyecto es una aplicación Android que muestra información sobre farmacias. La aplicación lee datos de un archivo JSON y permite al usuario ver detalles específicos de cada farmacia, como coordenadas, descripción, teléfono, etc.

## Estructura del Proyecto

### `MainActivity`

`MainActivity` es la actividad principal de la aplicación. Se encarga de mostrar la interfaz principal y gestionar la interacción del usuario con los botones y la visualización de datos.

#### Métodos

- `onCreate(savedInstanceState: Bundle?)`: Método principal que se ejecuta al crear la actividad. Inicializa los elementos de la interfaz y configura los botones.
- `toggleContent(action: () -> Unit)`: Alterna la visibilidad del contenido en la interfaz. Si el contenido está visible, lo oculta; de lo contrario, ejecuta la acción proporcionada.
- `showFarmacias()`: Muestra una lista de farmacias en la interfaz. Lee los datos del archivo JSON y crea vistas dinámicas para cada farmacia.
- `showSpecificField(field: String)`: Muestra un campo específico (título, coordenadas, enlace, descripción) de las farmacias en la interfaz.
- `showTelefonos()`: Muestra los números de teléfono de las farmacias en la interfaz.

### `FarmaciaDetailActivity`

`FarmaciaDetailActivity` es la actividad que muestra los detalles de una farmacia específica. Se abre cuando el usuario selecciona una farmacia en la `MainActivity`.

#### Métodos

- `onCreate(savedInstanceState: Bundle?)`: Método principal que se ejecuta al crear la actividad. Inicializa los elementos de la interfaz y muestra los datos de la farmacia seleccionada.

## Archivos XML

### `activity_main.xml`

Define la interfaz de usuario para `MainActivity`. Contiene un `ScrollView` con varios botones y un contenedor para mostrar las farmacias.

### `activity_farmacia_detail.xml`

Define la interfaz de usuario para `FarmaciaDetailActivity`. Contiene una `ImageView` para la imagen de fondo y dos `TextView` para mostrar las coordenadas y la descripción de la farmacia.

## Recursos

### `mapa_zaragoza.png`

Imagen utilizada como fondo en `FarmaciaDetailActivity`.

### `medicines_icon.png`

Icono utilizado para representar las farmacias en `MainActivity`.




