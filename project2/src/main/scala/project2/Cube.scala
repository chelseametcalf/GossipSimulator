package project2

import java.util
import scala.collection.mutable.Queue
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.LinkedList
import scala.util.Random
import java.util.Scanner
import scala.util.control.Breaks._

/**
 * Created by chelsea on 9/17/15.
 */

/**
 * Cube creator
 */
class Cube {
  var NODES: Int = 0
  var EDGES: Int = 144

  var cubeList = Array.ofDim[Int](EDGES,2)
  var neighborList = Array.ofDim[Int](6)
  var imperfectList = Array.ofDim[Int](7)

  var node1: Int = 0
  var node2: Int = 0

  var imperfect: Boolean = false

  def getRandomCubeNeighbor(input: String, idx: Int): Int = {
    var scan: Scanner = new Scanner(input)
    NODES = scan.nextInt()
    EDGES = scan.nextInt()
    println("Nodes: " + NODES + "    Edges: " + EDGES)

    for (i <- 0 to EDGES-1) {
      node1 = scan.nextInt()
      node2 = scan.nextInt()

      cubeList(i)(0) = node1
      cubeList(i)(1) = node2
    }

    var counter = 0
    for (i <- 0 to EDGES-1) {
      //println(cubeList(i)(0) + ", " + cubeList(i)(1))
      if (cubeList(i)(0) == idx) {
        if (checkDuplicates(cubeList(i)(1)) == false) {
          neighborList(counter) = cubeList(i)(1)
          counter = counter + 1
        }
      }
      if (cubeList(i)(1) == idx) {
        if (checkDuplicates(cubeList(i)(0)) == false) {
          neighborList(counter) = cubeList(i)(0)
          counter = counter + 1
        }
      }
    }

    // If imperfect, then add into neighborList a random node that is NOT in the neighborList
    if (imperfect == true) {
      for (i <- 0 to neighborList.size-1) {
        imperfectList(i) = neighborList(i)
      }

      breakable {
        for (i <- 0 to cubeList.size - 1) {
          if (checkDuplicates(cubeList(i)(1)) == false) {
            imperfectList(counter) = cubeList(i)(1)
            counter = counter + 1
            break
          }
          if (checkDuplicates(cubeList(i)(0)) == false) {
            imperfectList(counter) = cubeList(i)(0)
            counter = counter + 1
            break
          }
        }
      }
    }

    // Prints out neighbor list - used for debugging
    println("Neighbor List (3D): ")
    for (i <- 0 to neighborList.size-1) {
      println(neighborList(i))
    }

    // Prints out neighbor list for imperfect 3D - used for debugging
    println("Neighbor List (3D Imperfect): ")
    for (i <- 0 to imperfectList.size-1) {
      println(imperfectList(i))
    }

    var r = Random.nextInt(counter-1)
    //println("RANDOM: " + neighborList(r))
    neighborList(r)
  }

  def checkDuplicates(num: Int): Boolean = {
    var duplicate = false
    for (i <- 0 to neighborList.size-1) {
      if (num == neighborList(i)) {
        duplicate = true
      }
    }
    duplicate
  }


  // Generate the cube
  def generateCube(n: Int): String = {
    var SIDE = n    // Number of nodes in one side of the cube
    var links = ""  // Holds the final output
    var link = 0    // Counts the number of links

    for (row <- 0 to SIDE) {
      for (col <- 0 to SIDE) {
        for (depth <- 0 to SIDE) {
          var current = depth + (col * SIDE) + (row * SIDE * SIDE)

          // If not last depth
          if(depth != SIDE-1) {
            links += "%d %d\n".format(current, current+1)
            link = link + 1
          }

          // If not last col
          if(col != SIDE-1) {
            links += "%d %d\n".format(current, current+SIDE)
            link = link + 1
          }

          // If not last row
          if(row != SIDE-1) {
            links += "%d %d\n".format(current, current+(SIDE*SIDE))
            link = link + 1
          }
        }
      }
    }
    // return #Nodes, #Edges, links ...
    "%d %d\n%s".format(SIDE*SIDE*SIDE, link, links)
  }
}