package com.company;

import java.util.Vector;
import java.util.ArrayList;
import java.util.BitSet;

public class LRUPageFault extends PageFault {

    ArrayList<BitSet> pageState = null;

    public LRUPageFault(int pages){
        System.out.println(pages);
        pageState = new ArrayList<>(pages);
        for (int i = 0; i < pages; i++) {
            pageState.add(new BitSet(pages));
        }
    }

    public void replacePage (Vector mem , int virtPageNum , int replacePageNum , ControlPanel controlPanel, String instruction){
        int oldPage = findPage(mem);
        makeReplace(mem , oldPage, virtPageNum, controlPanel, instruction);
    }

    public void referencePage(int pageNum){
        //System.out.println("Reference " + pageNum);
        // When a page frame, k, is referenced then
        // all the bits of the k row are set to one
        pageState.get(pageNum).set(0, size());
        // and all the bits of the k column are set to zero
        for (int i = 0; i < size(); i++) {
            pageState.get(i).set(pageNum, false);
        }
    }

    private int findPage(Vector memory){
        int min_index = -1;
        // Find first virtual page which reference physical
        for(int i = 0; min_index == -1; i++){
            if((( Page ) memory.elementAt(i)).physical != -1)
                min_index = i;
        }

        BitSet min = pageState.get(min_index);
        for (int i = 0; i < size(); i++) {
            if((( Page ) memory.elementAt(i)).physical != -1 && compare(min, pageState.get(i)) == -1){
                min = pageState.get(i);
                min_index = i;
            }
        }
        return min_index;
    }

    private void makeReplace(Vector mem, int oldPageNum, int newPageNum, ControlPanel controlPanel, String instruction){
        Page oldPage = ( Page ) mem.elementAt( oldPageNum );
        Page newPage = ( Page ) mem.elementAt( newPageNum );
        newPage.R = 1;
        if(instruction.equals("WRITE")) {
            newPage.M = 1;
        }
        controlPanel.removePhysicalPage( oldPage.id );
        newPage.physical = oldPage.physical;
        controlPanel.addPhysicalPage( newPageNum, newPage.physical );

        oldPage.inMemTime = 0;
        oldPage.lastTouchTime = 0;
        oldPage.R = 0;
        oldPage.M = 0;
        oldPage.physical = -1;
    }

    private int size(){
        return pageState.size();
    }

    private int compare(BitSet lhs, BitSet rhs) {
        if (lhs.equals(rhs)) return 0;
        BitSet xor = (BitSet)lhs.clone();
        xor.xor(rhs);
        int firstDifferent = xor.length()-1;
        if(firstDifferent==-1)
            return 0;

        return rhs.get(firstDifferent) ? 1 : -1;
    }
}
