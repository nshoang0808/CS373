/* === YOUR WORK HERE ===
 * Fragment shader: you need to edit the code here!
 * First, you need to understand the Java program,
 * mainly understand how the variables in this shader program
 * are associated with buffers and variables defined  in your Java program. 
 * Also you need to understand how data are passed from 
 * your vertex shader to the fragment shader.
 * The changes you need to do here is 
 * (a) use the 'texture0' variable for applying a texture to the die
 * (b) compute the ambient+diffuse shading term for each pixel. This will need 
 *     to be multiplied with the texture color
 * (c) use the timer to further modulate the red and green channel of your pixels
*/
#version 330

in vec2 uv_coordinates_raster;
in vec3 normals_raster;
in vec3 light0_vector_raster; 

uniform sampler2D texture0;
uniform float timer;

out vec4 glFragColor; // user-defined variable replacing gl_FragColor in latest GLSL versions

void main() {
    
    const vec3 diffuse_light_color = vec3(0.25, 0.25, 0.5);
    const vec3 ambient_light_color = vec3(0.75, 0.75, 0.75);
    
    // you need to change the following lines (and use the above variables and constants)
    ( ambient_light_color + diffuse_light_color*max( dot(normals_raster, light0_vector_raster), 0));
    vec4 tmp = vec4(( ambient_light_color + diffuse_light_color*max( dot(normals_raster, light0_vector_raster), 0)), 1.0 );
    tmp = tmp + vec4(0.1*cos(0.01*timer), 0.0, 0.1*sin(0.01*timer), 0.0);
    glFragColor = texture(texture0, uv_coordinates_raster)*tmp;
}
