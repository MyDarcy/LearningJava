package com.darcy.main.cleancode_v1_0_3.Misc;

import java.util.*;

/**
 * Author by darcy
 * Date on 17-9-3 下午9:13.
 * Description:
 *
 * Clone an undirected graph. Each node in the graph contains
 * a label and a list of its neighbors.
 *
 */
public class P38_CloneGraph {

  static class UndirectedGraphNode {
    Integer label;
    List<UndirectedGraphNode> neighbors = new ArrayList<>();

    public UndirectedGraphNode(Integer label) {
      this.label = label;
    }
  }

  /**
   * 通过任意节点都可以利用DFS算法遍历整个图.
   *
   * DFS算法.
   *
   * O(n)的时间复杂度, O(n)的空间复杂度.
   *
   * A graph is simply represented by a graph node that serves as its starting point. In fact, the
   * starting point could be any other graph nodes and it does not affect the cloning algorithm.
   *
   * As each of its neighbors is a graph node too, we could recursively clone each of its
   * neighbors and assign it to each neighbor of the cloned node. We can easily see that it is
   * doing a depth-first traversal of each node.
   *
   * Note that the graph could contain cycles, for example a node could have a neighbor that
   * points back to it. Therefore, we should use a map that records each node’s copy to avoid
   * infinite recursion.
   *
   * @param graph
   * @return
   */
  public UndirectedGraphNode cloneGraph(UndirectedGraphNode graph) {
    if (graph == null) return null;
    // 节点和节点的副本.
    Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
    return DFS(graph, map);
  }
  private UndirectedGraphNode DFS(UndirectedGraphNode graph,
                                  Map<UndirectedGraphNode, UndirectedGraphNode> map) {
    // 原节点还没有复制过.
    if (map.containsKey(graph)) {
      return map.get(graph);
    }
    UndirectedGraphNode graphCopy = new UndirectedGraphNode(graph.label);
    map.put(graph, graphCopy);
    for (UndirectedGraphNode neighbor : graph.neighbors) {
      graphCopy.neighbors.add(DFS(neighbor, map));
    }
    return graphCopy;
  }


  /**
   *
   * BFS的做法.
   *
   * O(n)的时间复杂度, O(n)的空间复杂度.
   *
   * @param graph
   * @return
   */
  public UndirectedGraphNode cloneGraph2(UndirectedGraphNode graph) {
    if (graph == null) return null;
    Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
    Queue<UndirectedGraphNode> q = new LinkedList<>();
    q.add(graph);
    UndirectedGraphNode graphCopy = new UndirectedGraphNode(graph.label);
    map.put(graph, graphCopy);
    while (!q.isEmpty()) {
      UndirectedGraphNode node = q.poll();
      for (UndirectedGraphNode neighbor : node.neighbors) {
        // 该节点是多个节点的邻居节点, 并且副本已经产生.那么直接获取副本节点放入到该节点的副本节点的邻居节点.
        if (map.containsKey(neighbor)) {
          map.get(node).neighbors.add(map.get(neighbor));
        } else {
          UndirectedGraphNode neighborCopy =
              new UndirectedGraphNode(neighbor.label);
          map.get(node).neighbors.add(neighborCopy);
          map.put(neighbor, neighborCopy);
          q.add(neighbor);
        }
      }
    }
    return graphCopy;
  }

}