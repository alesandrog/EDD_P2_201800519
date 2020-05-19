/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.util.LinkedList;
import objetos.Libro;

/**
 *
 * @author USUARIO
 */
public class ArbolAvl {

    NodoAVL raiz;
    public String cat = "";
    public static ListaSimpleB resultado_busqueda;
    public LinkedList<NodoAVL> categorias_usuario;
    public String arbol = "";
    public int contadorNodos = 0;
    public int it = 0;
    String gdot = "";

    public ArbolAvl() {
        raiz = null;
    }

    public NodoAVL raizArbol() {
        return raiz;
    }

    public void insertar(String categoria) {
        raiz = insert(raiz, categoria);
    }

    NodoAVL insert(NodoAVL node, String categoria) {
        /* 1. Perform the normal BST insertion */
        if (node == null) {
            return (new NodoAVL(categoria));
        }

        if (categoria.compareToIgnoreCase(node.getCategoria()) < 0) {
            node.setIzquierda(insert(node.getIzquierda(), categoria));
        } else if (categoria.compareToIgnoreCase(node.getCategoria()) > 0) {
            node.setDerecha(insert(node.getDerecha(), categoria));
        } else // Equal keys are not allowed in BST  
        {
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.setAltura(1 + max(altura(node.getIzquierda()), altura(node.getDerecha())));

        /* 3. Get the balance factor of this ancestor  
         node to check whether this node became  
         unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then  
        // there are 4 cases  
        // Left Left Case  
        if (balance > 1 && categoria.compareToIgnoreCase(node.getIzquierda().getCategoria()) < 0) {
            return rightRotate(node);
        }

        // Right Right Case  
        if (balance < -1 && categoria.compareToIgnoreCase(node.getDerecha().getCategoria()) > 0) {
            return leftRotate(node);
        }

        // Left Right Case  
        if (balance > 1 && categoria.compareToIgnoreCase(node.getIzquierda().getCategoria()) > 0) {
            node.setIzquierda(leftRotate(node.getIzquierda()));
            return rightRotate(node);
        }

        // Right Left Case  
        if (balance < -1 && categoria.compareToIgnoreCase(node.getDerecha().getCategoria()) < 0) {
            node.setDerecha(rightRotate(node.getDerecha()));
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    public void preOrder(NodoAVL root) {
        if (root != null) {
            System.out.println(root.getCategoria());
            gdot += "node_" + it + "[label = \"" + root.getCategoria() + " : " + root.totalLibros + "\" width"
                    + "= 1.5 style = filled, fillcolor = lightblue];\n ";
            it++;
            preOrder(root.getIzquierda());
            preOrder(root.getDerecha());
        }
    }

    public void inOrder(NodoAVL root) {
        if (root != null) {
            inOrder(root.getIzquierda());
            System.out.println(root.getCategoria());
            gdot += "node_" + it + "[label = \"" + root.getCategoria() + " : " + root.totalLibros + "\" width"
                    + "= 1.5 style = filled, fillcolor = lightblue];\n ";
            it++;
            inOrder(root.getDerecha());
        }
    }

    public void postOrder(NodoAVL root) {
        if (root != null) {
            postOrder(root.getIzquierda());
            postOrder(root.getDerecha());
            System.out.println(root.getCategoria());
            gdot += "node_" + it + "[label = \"" + root.getCategoria() + " : " + root.totalLibros + "\" width"
                    + "= 1.5 style = filled, fillcolor = lightblue];\n ";
            it++;
        }
    }

    public void buscar_libro(NodoAVL root) {
        if (root != null) {
            System.out.println(root.getCategoria());
            preOrder(root.getIzquierda());
            preOrder(root.getDerecha());
        }
    }

    private NodoAVL rightRotate(NodoAVL y) {
        NodoAVL x = y.getIzquierda();
        NodoAVL T2 = x.getDerecha();
        // Perform rotation  
        x.setDerecha(y);
        y.setIzquierda(T2);
        // Update heights  
        y.setAltura(max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);
        x.setAltura(max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);

        // Return new root  
        return x;
    }

    private NodoAVL leftRotate(NodoAVL x) {
        NodoAVL y = x.getDerecha();
        NodoAVL T2 = y.getIzquierda();

        // Perform rotation  
        y.setIzquierda(x);
        x.setDerecha(T2);

        // Update heights  
        y.setAltura(max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);
        x.setAltura(max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);
        // Return new root  
        return y;
    }

    public void insertarLibro(String categoria, Libro libro) {
        NodoAVL aux = buscar(raiz, categoria);
        if (aux != null) {
            aux.totalLibros++;
            aux.getLibros().insertar(libro);
        }
    }

    public void busqueda_preOrder(NodoAVL root, String isbn) {
        if (root != null) {
            if (root.getLibros().perteneceCategoria(isbn)) {
                resultado_busqueda.insertar(new NodoSimpleB(root.getLibros().buscar(isbn)));
            }
            busqueda_preOrder(root.getIzquierda(), isbn);
            busqueda_preOrder(root.getDerecha(), isbn);
        }
    }

    public void eliminarPropietario(int prop) {
        categorias_usuario = new LinkedList<NodoAVL>();
        recopilar(raiz, prop);
    }

    public String dot_pre() {
        gdot = "";
        gdot += "digraph G{\n";
        gdot += "node[shape = box]\n";
        gdot += "rankdir = LR;\n";
        preOrder(raiz);
        it--;
        for (int i = 0; i < it; i++) {
            gdot += "node_" + i + "->" + "node_" + (i + 1) + ";\n";
        }
        it = 0;
        gdot += "}";
        return gdot;
    }

    public String dot_in() {
        gdot = "";
        gdot += "digraph G{\n";
        gdot += "node[shape = box]\n";
        gdot += "rankdir = LR;\n";
        inOrder(raiz);
        it--;
        for (int i = 0; i < it; i++) {
            gdot += "node_" + i + "->" + "node_" + (i + 1) + ";\n";
        }
        it = 0;
        gdot += "}";
        return gdot;
    }

    public String dot_pos() {
        gdot = "";
        gdot += "digraph G{\n";
        gdot += "node[shape = box]\n";
        gdot += "rankdir = LR;\n";
        postOrder(raiz);
        it--;
        for (int i = 0; i < it; i++) {
            gdot += "node_" + i + "->" + "node_" + (i + 1) + ";\n";
        }
        it = 0;
        gdot += "}";

        return gdot;
    }

    public void recopilar(NodoAVL root, int prop) {
        if (root != null) {
            if (root.propietario == prop) {
                categorias_usuario.add(root);
            }
            recopilar(root.getIzquierda(), prop);
            recopilar(root.getDerecha(), prop);
        }
    }

    public void busqueda_preOrderN(NodoAVL root, String nombre) {
        if (root != null) {
            root.getLibros().perteneceCategoriaN(nombre);
            if (root.getLibros().coincidencias > 0) {
                root.getLibros().buscar_nombre(nombre);
            }
            System.out.println(root.categoria + " Coincidencias: " + root.getLibros().coincidencias);
            busqueda_preOrderN(root.getIzquierda(), nombre);
            busqueda_preOrderN(root.getDerecha(), nombre);
        }
    }

    public void buscar_isbn(String isbn) {
        resultado_busqueda = new ListaSimpleB();
        busqueda_preOrder(raiz, isbn);
    }

    public void buscar_nombre(String nombre) {
        resultado_busqueda = new ListaSimpleB();
        busqueda_preOrderN(raiz, nombre);
    }

    public NodoAVL buscar_categoria(String categoria) {
        return buscar(raiz, categoria);
    }

    public ArbolB libros_categoria(String categoria) {
        resultado_busqueda = new ListaSimpleB();
        NodoAVL aux = buscar_categoria(categoria);
        return aux.getLibros();
    }

    public NodoAVL buscar(NodoAVL nodo, String categoria) {
        if (nodo == null) {
            return null;
        } else if (categoria.compareToIgnoreCase(nodo.getCategoria()) == 0) {
            return nodo;
        } else if (categoria.compareToIgnoreCase(nodo.getCategoria()) < 0) {
            return buscar(nodo.getIzquierda(), categoria);
        } else {
            return buscar(nodo.getDerecha(), categoria);
        }
    }

    public Libro buscar_libro(NodoAVL nodo, String isbn) {

        if (nodo != null) {
            if (nodo.getLibros().perteneceCategoria(isbn)) {
                return nodo.getLibros().buscar(isbn);
            }
            return buscar_libro(nodo.getIzquierda(), isbn);
        }
        return buscar_libro(nodo.getDerecha(), isbn);
    }

    private int getBalance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.getIzquierda()) - altura(nodo.getDerecha());
    }

    public int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getAltura();
    }

    public void eliminarCategoria(String categoria) {
        deleteNode(raiz, categoria);
    }

    NodoAVL minValueNode(NodoAVL node) {
        NodoAVL current = node;

        /* loop down to find the leftmost leaf */
        while (current.izquierda != null) {
            current = current.derecha;
        }

        return current;
    }

    NodoAVL deleteNode(NodoAVL root, String categoria) {
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (root == null) {
            return root;
        }

        // If the key to be deleted is smaller than  
        // the root's key, then it lies in left subtree  
        if (categoria.compareToIgnoreCase(root.getCategoria()) < 0) {
            root.izquierda = deleteNode(root.izquierda, categoria);
        } // If the key to be deleted is greater than the  
        // root's key, then it lies in right subtree  
        else if (categoria.compareToIgnoreCase(root.getCategoria()) > 0) {
            root.derecha = deleteNode(root.derecha, categoria);
        } // if key is same as root's key, then this is the node  
        // to be deleted  
        else {

            // node with only one child or no child  
            if ((root.izquierda == null) || (root.derecha == null)) {
                NodoAVL temp = null;
                if (temp == root.izquierda) {
                    temp = root.derecha;
                } else {
                    temp = root.izquierda;
                }

                // No child case  
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case  
                {
                    root = temp; // Copy the contents of  
                }                                // the non-empty child  
            } else {

                // node with two children: Get the inorder  
                // successor (smallest in the right subtree)  
                NodoAVL temp = minValueNode(root.derecha);

                // Copy the inorder successor's data to this node  
                root.setCategoria(temp.getCategoria());

                // Delete the inorder successor  
                root.derecha = deleteNode(root.derecha, temp.getCategoria());
            }
        }

        // If the tree had only one node then return  
        if (root == null) {
            return root;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        root.setAltura(max(altura(root.izquierda), altura(root.derecha)) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getBalance(root.izquierda) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case  
        if (balance > 1 && getBalance(root.izquierda) < 0) {
            root.derecha = leftRotate(root.derecha);
            return rightRotate(root);
        }

        // Right Right Case  
        if (balance < -1 && getBalance(root.izquierda) <= 0) {
            return leftRotate(root);
        }

        // Right Left Case  
        if (balance < -1 && getBalance(root.derecha) > 0) {
            root.derecha = rightRotate(root.izquierda);
            return leftRotate(root);
        }

        return root;
    }

    public void generarDot() {
        arbol = "";
        arbol += "digraph g {\n"
                + "node [shape = record,height=.1];";
        generarD(raiz);
        indexar(raiz);
        arbol += "}\n";

        System.out.println(arbol);
    }

    public void generarD(NodoAVL actual) {
        if (actual != null) {
            arbol += "node" + contadorNodos + "[label=\"";
            actual.indice = "node" + contadorNodos;
            arbol += "<f0>|" + actual.getCategoria() + " : " + actual.totalLibros + "| ";
            arbol += "\"];\n";
            contadorNodos++;
            generarD(actual.izquierda);
            generarD(actual.derecha);
        }
    }

    public void indexar(NodoAVL actual) {

        if (actual != null) {
            if (actual.izquierda != null) {
                arbol += actual.indice + "->" + actual.izquierda.indice + ";\n";
            }
            if (actual.derecha != null) {
                arbol += actual.indice + "->" + actual.derecha.indice + ";\n";
            }
            indexar(actual.izquierda);
            indexar(actual.derecha);
        }
    }
}
