//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Algo;

import java.util.ArrayList;

public class Node
{
    private static int _counter = 0;
    private int _id;
    private Node_Info _info;
    private ArrayList<Edge> _ni;
    private String _name;

    public Node(String s)
    {
        this.set_id(_counter++);
        this.set_ni(new ArrayList());
        this._info = new Node_Info();
        this.set_name(s);
    }

    public static int getCounter()
    {
        return _counter;
    }

    public int get_id()
    {
        return this._id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public Node_Info get_info()
    {
        return this._info;
    }

    public void set_pos(Node_Info ni)
    {
        this._info = ni;
    }

    public ArrayList<Edge> get_ni()
    {
        return this._ni;
    }

    public void set_ni(ArrayList<Edge> _ni)
    {
        this._ni = _ni;
    }

    public boolean hasEdge(int id)
    {
        boolean ans = false;

        for (int i = 0; i < this._ni.size(); ++i)
        {
            if (id == ((Edge) this._ni.get(i)).getInd())
            {
                ans = true;
            }
        }

        return ans;
    }

    public boolean add(Node n, double w)
    {
        boolean did = false;
        int nid = n.get_id();
        if (!this.hasEdge(nid))
        {
            did = true;
            Edge e = new Edge(nid, w);
            this._ni.add(e);
        }

        return did;
    }

    public double getDist()
    {
        return this._info._dist;
    }

    public ArrayList<String> getPath()
    {
        return this._info._temp_path;
    }

    public int degree()
    {
        return this._ni.size();
    }

    public String toString()
    {
        String ans = "Node: " + this._id + ", name, " + this._name + " ,|ni|, " + this._ni.size() + " , " + this._info;
        return ans;
    }

    public String get_name()
    {
        return this._name;
    }

    private void set_name(String name)
    {
        this._name = name;
    }

    public static void resetCounter(){
        _counter = 0;
    }
    
    public static void decreaseCounter() {
    	--_counter;
    }
}
