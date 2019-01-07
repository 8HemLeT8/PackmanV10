//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Algo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Graph
{
    public static final double EPS = 1.0E-6D;
    private ArrayList<Node> _nodes;
    private int _edge_count;

    public Graph()
    {
        _nodes = new ArrayList();
        _edge_count = 0;
    }

    public boolean add(Node d)
    {
        boolean ans = false;
        if (!this.exist(d))
        {
        	d.set_id(_nodes.size());
            this._nodes.add(d);
            ans = true;
        }

        return ans;
    }

    public int size()
    {
        return this._nodes.size();
    }

    public String toString()
    {
        String ans = "";
        ans = ans + this.size() + "\n" + this._edge_count;

        for (int i = 0; i < this.size(); ++i)
        {
            Node cr = (Node) this._nodes.get(i);
            ans = ans + cr + "\n";
        }

        return ans;
    }

    public void ResetGraph()
    {
        _nodes = new ArrayList();
        _edge_count = 0;
        Node.resetCounter();
    }

    public void toFile()
    {
        long t = (new Date()).getTime();
        String name = "Graph_" + this.size() + "_" + this._edge_count + "_" + t;
        this.toFile(name);
    }

    public void toFile(String f)
    {
        try
        {
            FileWriter fw = new FileWriter(f);
            PrintWriter os = new PrintWriter(fw);
            os.print(this.size() + "\n");

            int i;
            Node cr;
            for (i = 0; i < this.size(); ++i)
            {
                cr = (Node) this._nodes.get(i);
                os.print(i + " " + cr.get_info() + "\n");
            }

            os.print(this._edge_count);

            for (i = 0; i < this.size(); ++i)
            {
                cr = (Node) this._nodes.get(i);
                ArrayList<Edge> ni = cr.get_ni();

                for (int a = 0; a < ni.size(); ++a)
                {
                    int index = ((Edge) ni.get(a)).getInd();
                    double w = ((Edge) ni.get(a)).getW();
                    if (index > i)
                    {
                        Node ot = (Node) this._nodes.get(index);
                        os.print("\n" + i + " " + index + " " + w);
                    }
                }
            }

            os.close();
            fw.close();
        }
        catch (Exception var12)
        {
            var12.printStackTrace();
            System.err.println("Err: unable to write Graph file named: " + f);
        }

    }

    public void clear_meta_data()
    {
        for (int i = 0; i < this.size(); ++i)
        {
            ((Node) this._nodes.get(i)).get_info().init();
        }

    }

    public Node getNodeByIndex(int i)
    {
        Node ans = null;
        if (i >= 0 && i < this.size())
        {
            ans = (Node) this._nodes.get(i);
        }

        return ans;
    }

    public boolean exist(Node d)
    {
        return this.getNodeByName(d.get_name()) != null;
    }

    public int getNodeIndexByName(String s)
    {
        int ans = -1;

        for (int i = 0; ans == -1 && i < this.size(); ++i)
        {
            Node cr = (Node) this._nodes.get(i);
            String name = cr.get_name();
            if (s.equals(name))
            {
                ans = i;
            }
        }

        return ans;
    }

    public Node getNodeByName(String s)
    {
        Node ans = null;
        int ind = this.getNodeIndexByName(s);
        if (ind != -1)
        {
            ans = (Node) this._nodes.get(ind);
        }

        return ans;
    }

    public void addEdge(String a, String b, double w)
    {
        Node va = this.getNodeByName(a);
        Node vb = this.getNodeByName(b);
        if (va != null && vb != null)
        {
            va.add(vb, w);
            vb.add(va, w);
        }

    }
}
