package com.example.natha_000.a2_comp_project.Graphing_Stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natha_000 on 05/02/2017.
 */

public class StatsClasses {
    public static List<StatsClasses> classes = new ArrayList<StatsClasses>();
    public static int nextID = 1;
    public int id;
    public float classwidth;
    public float lowerbound;
    public float upperbound;
    public int frequency;
    public float freqdensity;
    public static int noOfClasses = 0;

    public StatsClasses(float lb, float ub, int freq) {
        this.id = nextID;
        this.lowerbound = lb;
        this.upperbound = ub;
        this.frequency = freq;
        this.classwidth = ub-lb;
        this.freqdensity = (float)freq/this.classwidth;
        addClass(this);
        nextID++;
        noOfClasses++;
    }

    public static void addClass(StatsClasses class_) {
        classes.add(class_);
    }

    private boolean contains(float bound) {
        if (this.lowerbound < bound && this.upperbound > bound) {
            return true;
        }
        return false;
    }

    private boolean contains(StatsClasses other) {
        if (this.lowerbound < other.lowerbound && this.upperbound > other.upperbound) {
            return true;
        }
        return false;
    }

    private boolean surrounds(StatsClasses other) {
        if (this.lowerbound > other.lowerbound && this.upperbound < other.upperbound) {
            return true;
        }
        return false;
    }

    private boolean contains(float lb, float ub) {
        if (this.lowerbound <= lb && this.upperbound >= ub) {
            return true;
        }
        return false;
    }

    public boolean surrounds(float lb, float ub) {
        if (this.lowerbound >= lb && this.upperbound <= ub) {
            return true;
        }
        return false;
    }

    public boolean setLB(float lb) {
        if (validBound(lb) && validBounds(lb,this.upperbound)) {
            this.lowerbound = lb;
            return true;
        }
        return false;
    }

    public boolean setUB(float ub) {
        if (validBound(ub) && validBounds(this.lowerbound,ub)) {
            this.upperbound = ub;
            return true;
        }
        return false;
    }

    public static boolean validBound(float bound){
        for (StatsClasses class_ : classes) {
            if (class_.contains(bound)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validBounds(float lb, float ub){
        if (ub-lb<=0){
            return false;
        }
        for (StatsClasses class_ : classes) {
            if (class_.contains(lb,ub) || class_.surrounds(lb,ub)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(StatsClasses other) {
        return this.id == other.id;
    }

    public static int findClassById(int id) {
        boolean found = false;
        boolean failed = false;
        int l = 0;
        int r = classes.size()-1;
        while (!found && !failed) {
            int m = (l+r)/2;
            if (l>r) {
                failed = true;
            } else if (classes.get(m).id<id) {
                l=m+1;
            } else if (classes.get(m).id>id) {
                r=m-1;
            } else if (classes.get(m).id==id) {
                return m;
            }
        }
        return -1;
    }

    public static StatsClasses getClassById(int id){
        for (int i=0; i<classes.size();i++){
            if (classes.get(i).id == id) {
                return classes.get(i);
            }
        }
        return null;
    }

    public static int findClassIndexByLb(float lb) {
        boolean found = false;
        boolean failed = false;
        int l = 0;
        int r = classes.size()-1;
        while (!found && !failed) {
            int m = (l+r)/2;
            if (l>r) {
                failed = true;
            } else if (classes.get(m).lowerbound<lb) {
                l=m+1;
            } else if (classes.get(m).lowerbound>lb) {
                r=m-1;
            } else if (classes.get(m).lowerbound==lb) {
                return m;
            }
        }
        return -1;
    }

    public static StatsClasses findClassByLb(float lb) {
        int classIndex = findClassIndexByLb(lb);
        if (classIndex == -1) {
            return null;
        }
        return classes.get(classIndex);
    }

    public static void reset() {
        classes = new ArrayList<StatsClasses>();
        nextID = 1;
        noOfClasses = 0;
    }

    public boolean delete() {
        return deleteClass(this.id);
    }

    public static boolean deleteClass(int id) {
        int classIndex = findClassById(id);
        if (classIndex != -1) {
            classes.remove(classIndex);
            noOfClasses--;
            return true;
        }
        return false;
    }

    public String toString() {
        String sentence = "("+Integer.toString(this.id)+","+Float.toString(this.lowerbound)+","+Float.toString(this.upperbound)+","+Integer.toString(this.frequency)+")";
        return sentence;
    }
}
