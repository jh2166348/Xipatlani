package proyectofinalprogra3;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Casilla extends JLabel{
    
    private int ancho=140;
    private int alto=140;
    private ImageIcon esconder = new ImageIcon(getClass().getResource("/com/juego/ram/res/hide.jpg"));//carpeta de ubicacion de las imagenes, direje ala imagen verde
    private ImageIcon imagen;
    private String sBandera="";
    private boolean congelado=false;
    
     /** 
      * constructor de clase
      */
    public Casilla( String name ){
        super();
        Dimension d = new Dimension(ancho,alto);
        setName(name);
        setSize( d );
        setPreferredSize( d );
        setText("");                
        setIcon( esconder );
        setVisible(true);        
        setOpaque(true);
        setCursor(new Cursor( Cursor.HAND_CURSOR ));
    }
    
    /** 
     * Muestra la imagen asignada a esta casilla
     */
    public void showImagen(){
        setIcon( imagen );
    }
    
    /**
     * Oculta la imagen
     */
    public void ocultarImagen(){
        if( !congelado ){
            setIcon( esconder );
        }
    }
    
    /**
     * Cuando una imagen es congelada, no se puede volver a ocultar hasta comenzar un nuevo juego
     * esto significa que ya ha sido encontrado su par
     */
    public void congelarImagen(boolean value){
        this.congelado=value;
    }
    
    /**
     * Metodo que retorna el valor boolean de una casilla si este esta o no congelado
     */
    public boolean isCongelado(){
        return this.congelado;
    }
    
    /**
     * Asigna la imagen que contendra la casilla
     */
    public void setBandera( String name ){
        this.sBandera = name;
        if( !name.equals("") ){            
            imagen = new ImageIcon(getClass().getResource("/com/juego/ram/res/"+name+".jpg"));        
        }        
    }
    
    /**
     * Retorna el nombre de la imagen que tenga asignada la casilla, si no tiene ninguna
     * retorna una cadena vacia
     */
    public String getNameImagen(){
        return sBandera;
    }
    
}
