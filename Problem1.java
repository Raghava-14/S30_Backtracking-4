//Time = 
//Space = O(w*h)

public class BuildingPlacement {
    // Directions for moving up, down, left, and right
    static final int[] ROWS = {-1, 1, 0, 0}; 
    static final int[] COLS = {0, 0, -1, 1};

    // Helper method to check if a cell is within grid bounds
    static boolean isInBounds(int row, int col, int w, int h) {
        return row >= 0 && row < h && col >= 0 && col < w;
    }

    // Method to compute the maximum distance from any empty lot to the nearest building
    static int getMaxDistance(int[][] grid, int w, int h) {
        int maxDistance = 0; // This will hold the maximum distance found
        Queue<int[]> queue = new LinkedList<>(); // Queue for BFS
        boolean[][] visited = new boolean[h][w]; // Visited matrix to keep track of cells

        // Initialize BFS from all building locations
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (grid[i][j] == 0) { // If the cell contains a building
                    queue.add(new int[]{i, j}); // Add the building to the BFS queue
                    visited[i][j] = true; // Mark the building cell as visited
                }
            }
        }

        // Perform BFS to calculate distances
        while (!queue.isEmpty()) {
            int size = queue.size();
            maxDistance++; // Increase distance for each level of BFS
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll(); // Get the next cell from the queue
                int r = cell[0];
                int c = cell[1];

                // Explore neighbors
                for (int d = 0; d < 4; d++) {
                    int newRow = r + ROWS[d];
                    int newCol = c + COLS[d];
                    if (isInBounds(newRow, newCol, w, h) && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true; // Mark the neighbor as visited
                        queue.add(new int[]{newRow, newCol}); // Add the neighbor to the queue
                    }
                }
            }
        }

        // Subtract one because the last increment will be one step further than the actual distance
        return maxDistance - 1;
    }

    // Method to find the optimal distance for placing buildings
    static int findOptimalDistance(int w, int h, int n) {
        int low = 0; // Minimum possible distance
        int high = Math.max(w, h); // Maximum possible distance
        int result = high; // Initialize result with the highest possible distance

        // Binary search for the optimal distance
        while (low <= high) {
            int mid = (low + high) / 2; // Midpoint distance
            int[][] grid = new int[h][w]; // Grid to place buildings
            boolean possible = canPlaceBuildings(grid, w, h, n, mid); // Check if buildings can be placed with this max distance

            if (possible) {
                result = mid; // If possible, update result and try for a smaller distance
                high = mid - 1;
            } else {
                low = mid + 1; // If not possible, try for a larger distance
            }
        }

        return result; // Return the optimal maximum distance
    }

    // Method to determine if we can place 'n' buildings such that the max distance is <= d
    static boolean canPlaceBuildings(int[][] grid, int w, int h, int n, int d) {
        int count = 0; // Counter for the number of buildings placed

        // Try placing buildings on the grid
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean canPlace = true; // Assume we can place a building here

                // Check if placing a building here satisfies the distance constraint
                for (int r = Math.max(0, i - d); r <= Math.min(h - 1, i + d); r++) {
                    for (int c = Math.max(0, j - d); c <= Math.min(w - 1, j + d); c++) {
                        if (Math.abs(r - i) + Math.abs(c - j) <= d && grid[r][c] == 0) {
                            canPlace = false; // Cannot place a building here if any building is too close
                            break;
                        }
                    }
                    if (!canPlace) break;
                }

                // Place the building if it is valid
                if (canPlace) {
                    grid[i][j] = 0; // Mark the cell as having a building
                    count++; // Increase the building count
                    if (count == n) return true; // Return true if we placed all buildings
                }
            }
        }
        
        return false; // Return false if not all buildings could be placed
    }

    public static void main(String[] args) {
        int w = 4; // Width of the grid
        int h = 4; // Height of the grid
        int n = 3; // Number of buildings

        // Find and print the optimal maximum distance
        int optimalDistance = findOptimalDistance(w, h, n);
        System.out.println("Optimal Distance: " + optimalDistance);
    }
}
