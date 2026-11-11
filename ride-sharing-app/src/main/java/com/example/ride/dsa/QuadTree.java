package com.example.ride.dsa;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    
    public static class Point {
        public double x; // Longitude
        public double y; // Latitude
        public Long driverId;

        public Point(double x, double y, Long driverId) {
            this.x = x;
            this.y = y;
            this.driverId = driverId;
        }
    }

    private static class Node {
        Point point;
        Node nw, ne, sw, se;
        
        Node(Point point) {
            this.point = point;
        }
    }

    private Node root;

    public void insert(Point point) {
        root = insertRec(root, point, 0, 0, 180, 90);
    }

    private Node insertRec(Node node, Point point, double xMin, double yMin, double xMax, double yMax) {
        if (node == null) {
            return new Node(point);
        }

        double xMid = (xMin + xMax) / 2;
        double yMid = (yMin + yMax) / 2;

        if (point.x < xMid && point.y >= yMid) {
            node.nw = insertRec(node.nw, point, xMin, yMid, xMid, yMax);
        } else if (point.x >= xMid && point.y >= yMid) {
            node.ne = insertRec(node.ne, point, xMid, yMid, xMax, yMax);
        } else if (point.x < xMid && point.y < yMid) {
            node.sw = insertRec(node.sw, point, xMin, yMin, xMid, yMid);
        } else {
            node.se = insertRec(node.se, point, xMid, yMin, xMax, yMid);
        }
        return node;
    }

    public List<Long> findNearby(double x, double y, double radius) {
        List<Long> results = new ArrayList<>();
        // Simple search bounding box
        searchRec(root, x, y, radius, 0, 0, 180, 90, results);
        return results;
    }

    private void searchRec(Node node, double x, double y, double radius, 
                           double xMin, double yMin, double xMax, double yMax, List<Long> results) {
        if (node == null) return;

        // 1. Check if this node is within the radius
        double distance = Math.sqrt(Math.pow(node.point.x - x, 2) + Math.pow(node.point.y - y, 2));
        if (distance <= radius) {
            results.add(node.point.driverId);
        }

        double xMid = (xMin + xMax) / 2;
        double yMid = (yMin + yMax) / 2;

        // 2. Check if the search area intersects with the quadrants before diving in
        // (This is the pruning step that makes it fast)
        
        // NW quadrant
        if (x - radius < xMid && y + radius >= yMid) searchRec(node.nw, x, y, radius, xMin, yMid, xMid, yMax, results);
        // NE quadrant
        if (x + radius >= xMid && y + radius >= yMid) searchRec(node.ne, x, y, radius, xMid, yMid, xMax, yMax, results);
        // SW quadrant
        if (x - radius < xMid && y - radius < yMid) searchRec(node.sw, x, y, radius, xMin, yMin, xMid, yMid, results);
        // SE quadrant
        if (x + radius >= xMid && y - radius < yMid) searchRec(node.se, x, y, radius, xMid, yMin, xMax, yMid, results);
    }
}