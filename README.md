# PDA Notes
PDA Notes es una app la cual esta diseñada para poseer notas y puede servir a varios usuarios a la par.

Para que las contraseñas sean mas seguras se hace uso de MD5 para poder tener la contraseña asegurada para cualquier tipo de problema informatico.

## Esquema de la BBDD

![](https://github.com/javiLeL/PDA-Notes/blob/main/BBDD_PDANotes.png)

## Requisitos funcionales

Al se una app de caracter escolar presnta ciertos requisitos minimos los cuales son:

### Elementos minimos 

- Elementos obligatorios
    - [ ] La aplicación debe contener mínimo 4 Layouts, de los cuales al menos dos, no pueden ser tipo ConstraintLayout.
    - [ ] Deben aparecer campos de texto variados (todos): Plain Text, AutocompleteTextView, Spinner, MultiAutocompleteTextView, Texto Multilinea.
    - [ ] Deben aparecer diferentes tipos de botones (*al menos cuatro de los indicados*): **ToggleButton, RadioButton, CheckBox, Switch, ImagenButton, Button**.
    - [ ] Deben aparecer *dos elementos* de los citados a continuación: **RatingBar, SeekBar, progressBar circular, progressBar Lineal**.
    - [ ] Deben aparecer **imágenes** que se deben almacenar en su carpeta de recursos
    correspondiente.
    - [ ] Las **fuentes de texto deben de ser variadas** y se deben **almacenar en la carpeta de recursos correspondiente**.
- Ventana de acceso/registro:
    - [ ] Los fondos de los Layouts deben ser todos **personalizados** con cualquiera de las opciones vistas en clase.
    - [ ] Debe aparecer **al menos una animación (***Lottie***)** que se debe de almacenar en su carpeta correspondiente.
    - [ ] Debe aparecer una **animación combinada** (*dos movimientos de libre elección*) que se
    debe almacenar en su carpeta correspondiente.
    - [ ] Se incluirá un **menú** con varias opciones, a la que una de ellas estará asociada a un **submenú**. Este elemento se vincula a su correspondiente **Toolbar** y los elementos necesarios se almacenarán en la carpeta correspondiente de recursos.
    - [ ] Un **menú contextual** asociado a todos los Plain Text de la ventana de formulario (se explica más adelante) para que el contenido escrito por el usuario se pueda poner en mayúsculas, minúsculas o en color ROJO. Los elementos necesarios se almacenarán en la carpeta correspondiente de recursos.
    - [x] Se utilizará persistencia para almacenar y validar todos los datos de los usuarios. Esta ventana no se contabiliza como parte de los layouts mínimos de diseño de la aplicación.
        - [x] Debe tener mínimo dos campos de texto: uno para escribir el nombre de usuario o email y otro  introducir la contraseña.
        - [x] Debe tener dos botones: uno que me permita acceder a la aplicación una vez haya informado los campos y otro que permita registrarse como nuevo usuario.


### Elementos de funcionalidad y diseño

- SplashScreen
    - [ ] Debe de incluir un SplashScreen
        - [ ] Debe ser la primera que se lanza al iniciar la aplicación.
        - [ ] No vuelve a ejecutarse. Cuando termine el tiempo de espera (5 segundos) no se puede volver a ver esta ventana.
- Ventana de Preferencias
    - [ ] Contiene tres categorías de diferentes tipos: ListPreference, CheckBoxPreference y EditTextPreference.
- Notificaciones, DatePicker y TimePicker
    - [ ] Debe aparecer selector de hora y de día. Una vez el usuario haya seleccionado un valor concreto, se enviará una notificación como recordatorio.
    - [ ] Al seleccionar la notificación, se abrirá otra ventana en la que simplemente se visualizan los datos seleccionados por el usuario (no se pueden editar, sólo visualizar).
    - [ ] Al pulsar el botón atrás de la pantalla del móvil se volverá a la misma ventana donde hemos seleccionado el día y la hora.
- Escuchadores
    - [ ] Se debe utilizar el escuchador específico para cada elemento

### Puntuacion Extra

- [ ] Se reserva UN PUNTO para elementos/funcionalidades que el alumno incluya además de las indicadas anteriormente.
    - Librerías externas
    - Fragments
    - Material Design