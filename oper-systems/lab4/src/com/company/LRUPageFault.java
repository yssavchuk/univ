package com.company;


import java.util.Vector;
import java.util.Arrays;

public class LRUPageFault extends PageFault {
    long[] pageState = null;
    int pagesNum = 0;
    public LRUPageFault(int pages){
        pagesNum = pages;
        pageState = new long[pages];
        System.out.println(Arrays.toString(pageState));
    }

    public void replacePage (Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel, String instruction){
        int oldPage = findPage(mem , virtPageNum , replacePageNum,  controlPanel, instruction);
        /////////////////////////////////////////////////////////////////////////////////////////
        int count = 0;
        int oldestPage = -1;
        int oldestTime = 0;
        int firstPage = -1;
        int map_count = 0;
        boolean mapped = false;

        while ( ! (mapped) || count != virtPageNum ) {
            Page page = ( Page ) mem.elementAt( count );
            if ( page.physical != -1 ) {
                if (firstPage == -1) {
                    firstPage = count;
                }
                if (page.inMemTime > oldestTime) {
                    oldestTime = page.inMemTime;
                    oldestPage = count;
                    mapped = true;
                }
            }
            count++;
            if ( count == virtPageNum ) {
                mapped = true;
            }
        }
        if (oldestPage == -1) {
            oldestPage = firstPage;
        }
        Page page = ( Page ) mem.elementAt( oldestPage );
        Page nextpage = ( Page ) mem.elementAt( replacePageNum );
        nextpage.R = 1;
        if(instruction.equals("WRITE")) {
            nextpage.M = 1;
        }
        controlPanel.removePhysicalPage( oldestPage );
        nextpage.physical = page.physical;
        controlPanel.addPhysicalPage( replacePageNum, nextpage.physical );
        page.inMemTime = 0;
        page.lastTouchTime = 0;
        page.R = 0;
        page.M = 0;
        page.physical = -1;
        /////////////////////////////////////////////////////////////////////////////////////////
        //makeReplace(mem , oldPage, virtPageNum, controlPanel, instruction);
    }

    public void referencePage(int pageNum){
        // When a page frame, k, is referenced then
        // all the bits of the k row are set to one
        pageState[pageNum] = Long.MAX_VALUE;
        System.out.println("To kill byte = " + pageNum);
        for(int i = 0; i < pagesNum; i++){
            // and all the bits of the k column are set to zero
            System.out.println("Before kill = " + pageState[i]);
            pageState[i] = killKthBit(pageState[i], pageNum );
            System.out.println("After kill = " + pageState[i]);
        }
        System.out.println(Arrays.toString(pageState));
    }

    private int findPage(Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel, String instruction){
        return 1;
    }

    private void makeReplace(Vector mem, int oldPageNum, int newPageNum, ControlPanel controlPanel, String instruction){
        Page oldPage = ( Page ) mem.elementAt( oldPageNum );
        Page newPage = ( Page ) mem.elementAt( newPageNum );
        newPage.R = 1;
        if(instruction.equals("WRITE")) {
            newPage.M = 1;
        }
        controlPanel.removePhysicalPage( oldPageNum );
        newPage.physical = oldPage.physical;
        controlPanel.addPhysicalPage( newPageNum, newPage.physical );
        oldPage.inMemTime = 0;
        oldPage.lastTouchTime = 0;
        oldPage.R = 0;
        oldPage.M = 0;
        oldPage.physical = -1;
    }

    private long killKthBit(long n, int k)
    {
        System.out.println(1 << (64 - k - 1));
        return n & ~(1 << (64 - k - 1)) ;
    }
}
