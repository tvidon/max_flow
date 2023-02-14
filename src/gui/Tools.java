package gui;

/**
 * Utility class for graphics
 */
class Tools{
    /**
     * Shorten a line
     * @param startX start x of the line
     * @param startY start y of the line
     * @param endX end x of the line
     * @param endY end y of the line
     * @param by how much to shorten by
     * @return {new end x, new end y}
     */
    static float[] shorten(float startX, float startY, float endX, float endY, float by){
        if (startX == endX){
            if (startY < endY){
                endY -= by;
                startY += by;
            }
            else if (startY > endY){
                endY += by;
                startY -= by;
            }
        }
        else if (startY == endY){
            if (startX < endX){
                endX -= by;
                startX += by;
            }
            else if (startX > endX){
                endX += by;
                startX -= by;
            }
        }
        else{
            float dX = endX - startX;
            float dY = endY - startY;
            float length = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
            float coef = (length - by) / length;
            endX = startX + dX * coef;
            endY = startY + dY * coef;
        }
        float[] out = {endX, endY};
        return out;
    }

    /**
     * Find the coordinates of the corners for an arrow
     * @param startX start x of the line on which to put the arrow
     * @param startY start y of the line on which to put the arrow
     * @param endX end x of the line on which to put the arrow
     * @param endY end y of the line on which to put the arrow
     * @param height height of the tip of the arrow
     * @param width width of the tip of the arrow
     * @return {x of the first corner of the arrow, y of the first corner of the arrow, x of the second corner of the arrow, y of the second corner of the arrow}
     */
    static float[] arrow(float startX, float startY, float endX, float endY, float height, float width){
        // works by finding the line at a right angle
        float c1X = 0; // x of corner 1
        float c1Y = 0; // y of corner 1
        float c2X = 0; // x of corner 2
        float c2Y = 0; // y of corner 2
        float halfWidth = width/2;
        if (startX == endX){
            c1X = startX - halfWidth;
            c2X = startX + halfWidth;
            if (startY < endY){
                c1Y = endY - height;
                c2Y = endY - height;
            }
            else if (startY > endY){
                c1Y = endY + height;
                c2Y = endY + height;
            }
        }
        else if (startY == endY){
            c1Y = startY - halfWidth;
            c2Y = startY + halfWidth;
            if (startX < endX){
                c1X = endX - height;
                c2X = endX - height;
            }
            else if (startX > endX){
                c1X = endX + height;
                c2X = endX + height;
            }
        }
        else{
            float coef = -1 / ((endY - startY) / (endX - startX));
            float[] mid = shorten(startX, startY, endX, endY, height);
            float midX = mid[0];
            float midY = mid[1];
            float someX = midX + 1;
            float baseY = midY - coef * midX;
            float someY = baseY + coef * someX;
            float[] c1 = shorten(someX, someY, midX, midY, -halfWidth);
            float[] c2 = shorten(someX, someY, midX, midY, halfWidth);
            c1X = c1[0];
            c1Y = c1[1];
            c2X = c2[0];
            c2Y = c2[1];
        }
        float[] out = {c1X, c1Y, c2X, c2Y};
        return out;
    }
}
