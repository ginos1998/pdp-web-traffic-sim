# pdp-web-traffic-sim

## Introduccion
Para el trabajo practico numero 2 de _Paradigmas de Programacion_, se prupuso la creacion de un simulador de trafico web. 
El mismo se realizo en el lenguaje de programacion Kotlin (con jdk-17), utilizando programacion orientada a objetos. 
La consigna propuesta se encuentra en la siguiente carpeta [enunciado](https://github.com/ginos1998/pdp-web-traffic-sim/tree/develop/doc/consigna).

## Desarrollo y funcionamiento
Para el desarrollo del simulador se siguio el siguiente diagrama de clases, las cuales representan los principales
modelos del sistema:
[![Diagrama de clases](https://github.com/ginos1998/pdp-web-traffic-sim/blob/develop/doc/package.png)

El simulador se ejecuta a partir de la clase `Main`, la cual tiene como primera tarea la lectura de un archivo externo
para obtener los datos de entrada, como _Routers, Terminales_ y la conexion entre ellos. Este archivo debe seguir el 
formato propuesto en [test](https://github.com/ginos1998/pdp-web-traffic-sim/blob/develop/src/main/resources/test.xlsx).

Luego, el programa empezara a crear paginas (mensajes) aleatorias, con origen y destino aleatorios, y las enviara a traves de los
routers hasta llegar a su destino. Cuando una pagina pasa por su Router de origen, la misma es dividida en paquetes.
Estos paquetes son enviados a traves de los routers hasta llegar a su destino, donde se vuelven a unir para formar la pagina.
El sistema utiliza el algoritmo de _Dijkstra_ para encontrar el camino mas corto entre dos routers, para enviar los paquetes.
Cuando todos los paquetes han llegado a su router de destino, se vuelve a unir la pagina y se envia a la terminal de destino.

En la siguiente imagen podemos ver como los paquetes se pueden mezclar con otros paquetes de otras paginas, en un router. 
Esto favorece la eficiencia del sistema, ya que se aprovecha el ancho de banda de los enlaces.
[![debug](https://github.com/ginos1998/pdp-web-traffic-sim/blob/develop/doc/Captura%20desde%202023-12-11%2020-34-58.png)

Al mismo tiempo que sucede lo explicado anteriormente, el sistema va mostrando por consola el estado de los routers,
terminales y paginas, para poder ver el funcionamiento del mismo. En la siguiente imagen podemos ver un ejemplo de esto:
[![sim](https://github.com/ginos1998/pdp-web-traffic-sim/blob/develop/doc/Captura%20desde%202023-12-11%2020-29-42.png)
[![sim2](https://github.com/ginos1998/pdp-web-traffic-sim/blob/develop/doc/Captura%20desde%202023-12-11%2020-36-13.png)
