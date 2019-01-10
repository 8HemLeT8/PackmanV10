//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Algo;

import java.util.ArrayList;

import Geom.Point3D;

public class Node_Info
{
    public static final int WHITE = 0;
    public static final int GRAY = 1;
    public static final int BLACK = 2;
    public static final int ERR = -1;
    public int _color;
    public double _dist;
    public ArrayList<String> _temp_path;
    public int _count_ni;
    public Point3D point;

    public Node_Info()
    {
        this((Point3D) null);
    }

    public Node_Info(Point3D p)
    {
        this.init();
        this.point = p;
    }
    
    public Point3D getPoint() {
    	return this.point;
    }

    public void init()
    {
        this._color = 0;
        this._dist = 0.0D;
        this._temp_path = new ArrayList();
        this._count_ni = 0;
    }

    public String toString()
    {
        String ans = " dist," + this._dist + ", path:";

        for (int i = 0; i < this._temp_path.size(); ++i)
        {
            ans = ans + "," + (String) this._temp_path.get(i);
        }

        return ans;
    }
}
