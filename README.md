# PDA Notes
PDA Notes es una app la cual esta diseñada para poseer notas y puede servir a varios usuarios a la par.

Para que las contraseñas sean mas seguras se hace uso de MD5 para poder tener la contraseña asegurada para cualquier tipo de problema informatico.

## Esquema de la BBDD

![](https://github.com/javiLeL/PDA-Notes/blob/main/BBDD_PDANotes.png)

## Requisitos funcionales

Al se una app de caracter escolar presnta ciertos requisitos minimos los cuales son:

### Elementos minimos 

- Elementos obligatorios
    - [x] La aplicación debe contener mínimo 4 Layouts, de los cuales al menos dos, no pueden ser tipo ConstraintLayout.
    - [x] Deben aparecer campos de texto variados (todos): 
        - [x] Plain Text
        - [x] AutocompleteTextView
        - [x] Spinner
        - [x] MultiAutocompleteTextView
        - [x] Texto Multilinea
    - [x] Deben aparecer diferentes tipos de botones (*al menos cuatro de los indicados*): 
        - [ ] ~**ToggleButton**~
        - [x] **RadioButton**
        - [x] **CheckBox**
        - [ ] ~**Switch**~
        - [x] **ImagenButton**
        - [x] **Button**
    - [x] Deben aparecer *dos elementos* de los citados a continuación:
        - [ ] ~**RatingBar**~
        - [ ] ~**SeekBar**~
        - [x] **ProgressBar circular**
        - [x] **progressBar Lineal**.
    - [x] Deben aparecer **imágenes** que se deben almacenar en su carpeta de recursos
    correspondiente.
    - [x] Las **fuentes de texto deben de ser variadas** y se deben **almacenar en la carpeta de recursos correspondiente**.
- Ventana de acceso/registro:
    - [x] Los fondos de los Layouts deben ser todos **personalizados** con cualquiera de las opciones vistas en clase.
    - [x] Debe aparecer **al menos una animación (***Lottie***)** que se debe de almacenar en su carpeta correspondiente.
    - [x] Debe aparecer una **animación combinada** (*dos movimientos de libre elección*) que se
    debe almacenar en su carpeta correspondiente.
    - [x] Se incluirá un **menú** con varias opciones, a la que una de ellas estará asociada a un **submenú**. Este elemento se vincula a su correspondiente **Toolbar** y los elementos necesarios se almacenarán en la carpeta correspondiente de recursos.
    - [x] Un **menú contextual** asociado a todos los Plain Text de la ventana de formulario *(se explica más adelante)* para que el contenido escrito por el usuario se pueda poner en **mayúsculas, minúsculas o en color ROJO**. Los elementos necesarios se almacenarán en la carpeta correspondiente de recursos.
    - [x] Se utilizará persistencia para almacenar y validar todos los datos de los usuarios. Esta ventana no se contabiliza como parte de los layouts mínimos de diseño de la aplicación.
        - [x] Debe tener mínimo dos campos de texto: uno para escribir el nombre de usuario o email y otro  introducir la contraseña.
        - [x] Debe tener dos botones: uno que me permita acceder a la aplicación una vez haya informado los campos y otro que permita registrarse como nuevo usuario.


### Elementos de funcionalidad y diseño

- SplashScreen
    - [x] Debe de incluir un SplashScreen
        - [x] Debe ser la primera que se lanza al iniciar la aplicación.
        - [x] No vuelve a ejecutarse. Cuando termine el tiempo de espera (5 segundos) no se puede volver a ver esta ventana.
- Ventana de Preferencias
    - [x] Contiene tres categorías de diferentes tipos: ListPreference, CheckBoxPreference y EditTextPreference.
- Notificaciones, DatePicker y TimePicker
    - [x] Debe aparecer selector de hora y de día. Una vez el usuario haya seleccionado un valor concreto, se enviará una notificación como recordatorio.
    - [x] Al seleccionar la notificación, se abrirá otra ventana en la que simplemente se visualizan los datos seleccionados por el usuario (no se pueden editar, sólo visualizar).
    - [x] Al pulsar el botón atrás de la pantalla del móvil se volverá a la misma ventana donde hemos seleccionado el día y la hora.
- Escuchadores
    - [x] Se debe utilizar el escuchador específico para cada elemento

### Puntuacion Extra

- [ ] Se reserva UN PUNTO para elementos/funcionalidades que el alumno incluya además de las indicadas anteriormente.
    - [ ] Librerías externas
    - [ ] Fragments
    - [x] Material Design

# Bibliografia

- [ProgresBar](https://gist.github.com/codinginflow/477606b85ed11c537a81e80224361878)