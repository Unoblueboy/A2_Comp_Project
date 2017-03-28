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

    static List<StatsClasses> mergeSort(List<StatsClasses> classes) {
        int n = classes.size();
        if (n <= 1){
            return classes;
        }
        List<StatsClasses> l1 = mergeSort(classes.subList(0, (int) Math.floor(n/2)));
        List<StatsClasses> l2 = mergeSort(classes.subList((int) Math.floor(n/2),n));


        return merge(l1,l2);
    }

    private static List<StatsClasses> merge(List<StatsClasses> l1, List<StatsClasses> l2){
        List<StatsClasses> l3 = new ArrayList<StatsClasses>();
        int l1counter = 0;
        int l2counter = 0;
        while (l1counter!=l1.size() && l2counter!=l2.size()) {
            if (l1.get(l1counter).lowerbound>l2.get(l2counter).lowerbound) {
                l3.add(l2.get(l2counter));
                l2counter++;
            } else {
                l3.add(l1.get(l1counter));
                l1counter++;
            }
        }

        while (l1counter!=l1.size()) {
            l3.add(l1.get(l1counter));
            l1counter++;
        }

        while (l2counter!=l2.size()) {
            l3.add(l2.get(l2counter));
            l2counter++;
        }
        return l3;
    }

    public static List<StatsClasses> getSortedClasses() {
        return mergeSort(classes);
    }

    public boolean contains(float bound) {
        if (this.lowerbound < bound && this.upperbound > bound) {
            return true;
        }
        return false;
    }

    public boolean contains(StatsClasses other) {
        return contains(other.lowerbound,other.upperbound);
    }

    public boolean contains(float lb, float ub) {
        if (this.lowerbound <= lb && this.upperbound >= ub) {
            return true;
        }
        return false;
    }

    public boolean surrounds(StatsClasses other) {
        if (this.lowerbound >= other.lowerbound && this.upperbound <= other.upperbound) {
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
            if (class_.contains(lb,ub) || class_.surrounds(lb,ub) || !validBound(lb) || !validBound(ub)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(StatsClasses other) {
        return this.id == other.id;
    }

    public static int findClassIndexById(int id) {
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
        int classIndex = findClassIndexById(id);
        if (classIndex == -1) {
            return null;
        }
        return classes.get(classIndex);
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

    public static StatsClasses getClassByLb(float lb) {
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
        int classIndex = findClassIndexById(id);
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
