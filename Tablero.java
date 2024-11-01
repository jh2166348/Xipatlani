package proyectofinalprogra3;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Tablero extends JPanel{
    //array con los nombres de las imagenes 8 en total para 16 pares
    private String[] band = {"atole","comal","elote","mariposa","popote","tortuga","chicle","chocolate"};
    
    private int fila =4;
    private int col = 4;
    private int ancho_casilla=140; //esta parte es muy importante, ya que las imagenes deben tener estas dimensiones
    //140X140 PIXELES
    
    public boolean play = false;
    
    int c=0;
    Casilla c1;
    Casilla c2;
    int aciertos=0;
    
    /** Constructor de clase */
    public Tablero(){
        super();
        //propiedades
        setBorder( BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout( new java.awt.GridLayout(fila, col) );        
        Dimension d= new Dimension( (ancho_casilla*col),(ancho_casilla*fila)  );        
        setSize(d);
        setPreferredSize(d);
        //crea instancias de casillas para crear el tablero 
        int count=0;
        for(int i=1;i<=(fila*col);i++){
            Casilla p = new Casilla( String.valueOf(i) );            
            p.setBandera( band[count] );
            count++;
            count = (count>=band.length)? 0:count++;
            p.showImagen();
            p.addMouseListener( new juegoMouseListener() );    
            this.add( p );
        }        
        setVisible(true);        
    }
    
    /**
     * Inicia juegos
     * - llena las casillas con pares de banderas 
     */
    public void comenzarJuego(){
        aciertos=0;
        play=true;
        Component[] componentes = this.getComponents();         
        //Quita las imagenes en cara el derecho
        for( int i=0; i< componentes.length ;i++){
            ((Casilla)componentes[i]).congelarImagen(false);
            ((Casilla)componentes[i]).ocultarImagen();            
            ((Casilla)componentes[i]).setBandera( "" );
        }
        //coloca nuevo orden aleatorio de banderas
        for( int i=0; i< componentes.length ;i++){
            int n = (int) (Math.random()*(band.length));        
            if( !existe(band[n]) ){//comprueba que bandera no este asignada mas de 2 veces                
                ((Casilla)componentes[i]).setBandera( band[n] );
            }else{
                i--;
            }
        }
        
    }
    
    
    /**
     * Metodo que comprueba que una casilla existe
     */
    private boolean existe( String bandera ){  
        int count=0;
        Component[] componentes = this.getComponents(); 
        for( int i=0; i<componentes.length;i++ ) {
            if( componentes[i] instanceof Casilla ) {
                if( ((Casilla)componentes[i]).getNameImagen().equals( bandera ) ) {
                    count++;
                }
            }
        }        
        return (count==2)? true:false;   
    }
    
    /**
     * Clase que implemenenta un MouseListener para la captura de eventos del mouse
     */
    class juegoMouseListener implements MouseListener{        
        
        @Override
        public void mouseClicked(MouseEvent e) {         
            
            if( play ){
                c++;//lleva la cuenta de los click realizados en las casillas            
                if( c==1 ){ //primer click
                    c1=((Casilla) e.getSource()); //obtiene objeto
                    if( !c1.isCongelado() ){ //valida que este seleccionada una imagen
                        c1.showImagen(); // y la muestra     
                        System.out.println("Primera Imagen Seleccionada: " + c1.getNameImagen() );    
                    }else{//no toma en cuenta en contador de los clicks
                      c=0;   
                    }                
                }else if( c==2 && !c1.getName().equals( ((Casilla) e.getSource()).getName() ) ){//segundo click
                    c2=((Casilla) e.getSource()); 
                    if( !c2.isCongelado() ){//valida que este seleccionada una imagen
                        c2.showImagen();// y la muestra     
                        System.out.println("Segunda Imagen Seleccionada: " + c2.getNameImagen() );    
                        //compara imagenes
                        Animacion ani = new Animacion( c1, c2 );
                        ani.execute();
                    }
                    c=0;//contador de click a 0
                }else{ //mas de 2 clic consecutivos no toma en cuenta
                    c=0; 
                }
            }else{
                System.out.println("Para jugar: FILE -> JUGAR");
            }
            
            
        }
        // acciones del mouse
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
    // Validacion que compara las imagenes
    class Animacion extends SwingWorker<Void, Void>{
        private Casilla casilla1;
        private Casilla casilla2;
        
        public Animacion(Casilla value1, Casilla value2){//traes los valores de c1 y c2 obtenidos del mouseClicked
            this.casilla1= value1;
            this.casilla2= value2;
        }
        
        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("doInBackground: procesando imagenes...");            
            //espera 1 segundo 
            Thread.sleep( 1000 );                
            if( casilla1.getNameImagen().equals( casilla2.getNameImagen() ) ){//son iguales
                casilla1.congelarImagen(true);
                casilla2.congelarImagen(true);
                System.out.println("doInBackground: imagenes son iguales");    
                aciertos++;
                if( aciertos == 8 ){//Si se llega a 8 aciertos gana o bien aprende
                    System.out.println("doInBackground: Usted acaba de aprender 8 nuevas palabras en NáHUATL!");  
                    JOptionPane.showMessageDialog(null,"Usted acaba de aprender 8 nuevas palabras en NáHUATL!");
                }
            }            
            else{//si no son iguales las vuelve a ocultar
                casilla1.ocultarImagen();
                casilla2.ocultarImagen();
                System.out.println("doInBackground: imagenes no son iguales");    
            }
            return null;
        }
    
    }
    
}
