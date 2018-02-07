import java.awt.event.*;
import javax.swing.JFrame;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.awt.GLCanvas;

public class GLexample extends JFrame implements GLEventListener, KeyListener {

    // canvas and its size
    private final GLCanvas canvas;
    private int winW = 512, winH = 512;

    // light/material properties
    private final float lightPos[] = {1.0f, 1.0f, 1.0f, 0.0f}; // directional
    private final float light0_ambient[] = {1f, 1f, 1f, 1f};
    private final float light0_diffuse[] = {1f, 1f, 1f, 1f};
    private final float light0_specular[] = {1f, 1f, 1f, 1f};    
    private final float mat_ambient[] = {.1f, .1f, .1f, 1f};
    private final float mat_diffuse[] = {.2f, .2f, .6f, 1f};
    private final float mat_diffuse2[] = {.2f, .6f, .2f, 1f};    
    private final float mat_specular[] = {.5f, .5f, .5f, 1f};
    private final float mat_shininess[] = {128}; //range[0,128]        

    // gl context variables
    private GL2 gl;
    private final GLU glu = new GLU();
    private final GLUT glut = new GLUT();

    public static void main(String args[]) {
        new GLexample();
    }

    // constructor
    public GLexample() {
        super("OpenGL demo");
        canvas = new GLCanvas();
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        getContentPane().add(canvas);
        setSize(winW, winH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        canvas.requestFocus();
    }

    // gl display function
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
       
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light0_ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light0_specular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse, 0);        
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
 
        gl.glLoadIdentity();
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);

        //// uncomment the commented lines below and in drawShape() / drawShape2()
        //// to check an alternative way to specify materials
        //// if you do this, comment the glMaterialfv commands
//        gl.glEnable(GL2.GL_COLOR_MATERIAL);               
//        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
        
        drawShape();
        
        drawShape2();
    }

    // draw the current shape
    public void drawShape() {
        // shade the current shape         
//        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, mat_diffuse, 0);
//        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, mat_specular, 0);
//        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mat_shininess, 0);
//        gl.glColor3f(.1f,3f, .4f);        
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
        glut.glutSolidSphere(1.0f, 16, 16);
    }
    
    // draw the current shape
    public void drawShape2() {
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, mat_diffuse2, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, mat_shininess, 0);
        //gl.glColor3f(.6f,.2f, .2f);        
        gl.glLoadIdentity();
        gl.glTranslatef(-1.0f, 0.0f, -10.0f);
        glut.glutSolidCube(1.0f);
    }    

    // initialization
    public void init(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
        gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1);

        gl.glDisable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LESS);
        gl.glCullFace(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);                

        // set clear color: this determines the background color (which is dark gray)
        gl.glClearColor(.3f, .3f, .3f, 1f);
        gl.glClearDepth(1.0f);        
    }

    // reshape callback function: called when the size of the window changes
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        winW = width;
        winH = height;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(30.0f, (float) width / (float) height, 0.1f, 20.0f);
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }


    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
        }
        canvas.display();
    }

    // these event functions are not used
    // but may be useful in the future
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        // TODO Auto-generated method stub
    }    
    
    public void dispose(GLAutoDrawable glautodrawable) {
    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


}
