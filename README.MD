#Santtium

### Ejemplo de conexión con STB y el driver de Santtium

Ejecución desde consola

    ./gradlew cucumber
    ./gradlew cucumber --tags @tag -Dkey_device='ID DEVICE'

    
Esta es otra forma de correr desde consola
    
    ./gradlew test
    ./gradlew test -Dcucumber.filter.tags="@tag" -Dkey_device='ID DEVICE'

Tambien se puede ejecutar desde la clase Runner
