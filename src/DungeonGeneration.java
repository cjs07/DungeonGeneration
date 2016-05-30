import java.util.Random;

public class DungeonGeneration {

    static int[][] layout;
    static int minRooms = 50;
    static int maxRooms = 100;
    static int maxSubbranches = 10;
    static int maxBranchesPerRoom = 4;
    static int maxRoomsPerBranch = 8;

    static int subbranches;
    static int totalRooms;
    static int totalBranches;

    static Random rng;

    public static void main (String[] args) {
        layout = new int[25][25];
        for (int x = 0; x < layout.length; x++) {
            for (int y = 0; y < layout.length; y++) {
                layout[x][y] = 0;
            }
        }

        rng = new Random();

        layout[(int)Math.floor(layout.length/2)][(int)Math.floor(layout[0].length/2)] = 2;

        int mainBranches = rng.nextInt(4)+1;
        int branch1 = rng.nextInt(4);
        int branch2 = rng.nextInt(4);
        int branch3 = rng.nextInt(4);
        int branch4 = rng.nextInt(4);

        totalBranches += mainBranches;
        totalRooms += 1;
        for (int i = 0; i < mainBranches; i++) {
            if (i==0) {
                generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch1, false);
            } else if (i==1) {
                generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch2, false);
            } else if (i==2) {
                generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch3, false);
            } else if (i==3) {
                generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch4, false);
            }
        }
        validate();

        for (int[] aLayout : layout) {
            for (int y = 0; y < layout[0].length; y++) {
                if (aLayout[y] != 0) {
                    System.out.print(aLayout[y] + "    ");
                }
            }
            System.out.print("\n");
        }
    }

    static void generateBranch (int x, int y, int side, boolean canZero) {
        int currentX, currentY, xOffset, yOffset;
        switch (side) {
            case 0:
                currentX = x-1;
                currentY = y;
                xOffset = -1;
                yOffset = 0;
                break;
            case 1:
                currentX = x;
                currentY = y-1;
                xOffset = 0;
                yOffset = -1;
                break;
            case 2:
                currentX = x+1;
                currentY = y;
                xOffset = 1;
                yOffset = 0;
                break;
            case 3:
                currentX = x;
                currentY = y+1;
                xOffset = 0;
                yOffset = 1;
                break;
            default:
                return;
        }

        int rooms = rng.nextInt(maxRoomsPerBranch);
        if (rooms == 0 && !canZero) {
            rooms++;
        }
        if ((totalRooms + rooms) > maxRooms) {
            return;
        }
        totalRooms += rooms;
        for (int i = 0; i < rooms; i++) {
            if (currentX < 0 || currentX >= layout.length ||  currentY < 0 || currentY >= layout[0].length) {
                return;
            }
            if (layout[currentX][currentY] == 2) {
                return;
            }
            layout[currentX][currentY] = 1;
            currentX += xOffset;
            currentY += yOffset;

            //generate more branches
            int branches = rng.nextInt(maxBranchesPerRoom*3);
            if (branches <= 2) {
                branches = 0;
            } else if (branches > 2 && branches <= 5) {
                branches = 1;
            } else if(branches > 5 && branches <= 8) {
                branches = 2;
            } else if (branches > 8 && branches <= 10 ) {
                branches = 3;
            } else if (branches == 11) {
                branches = 4;
            } else if (branches == 12) {
                branches = 0;
            } else {
                branches = 0;
            }

            for (int j = 0; j < branches; j++) {
                if (subbranches >= maxSubbranches){
                    subbranches = 0;
                    return;
                } else {
                    subbranches++;
                    generateBranch(currentX, currentY, rng.nextInt(4), true);
                }
            }
        }
    }

    static void validate() {
        if (totalRooms < minRooms) {
            totalRooms = 0;
            totalBranches = 0;
            subbranches = 0;
            layout[(int)Math.floor(layout.length/2)][(int)Math.floor(layout[0].length/2)] = 2;

            int mainBranches = rng.nextInt(4)+1;
            int branch1 = rng.nextInt(4);
            int branch2 = rng.nextInt(4);
            int branch3 = rng.nextInt(4);
            int branch4 = rng.nextInt(4);

            totalBranches += mainBranches;
            totalRooms += 1;
            for (int i = 0; i < mainBranches; i++) {
                if (i==0) {
                    generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch1, false);
                } else if (i==1) {
                    generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch2, false);
                } else if (i==2) {
                    generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch3, false);
                } else if (i==3) {
                    generateBranch((int)Math.ceil(layout.length/2), (int)Math.ceil(layout[0].length/2), branch4, false);
                }
            }
            validate();
        }
    }
}
