/**
 * Contributors: Gautam Sirdeshmukh and Amber Potter
 */

public class LinkStrand implements IDnaStrand{

    private class Node {
        String info;
        Node next;

        public Node(String s, Node n) {
            info = s;
            next = n;
        }

        public Node(String s) {
            info = s;
            next = null;
        }
    }

    private Node myFirst, myLast, myCurrent;
    private long mySize;
    private int myAppends, myIndex, myLocalIndex;

    public LinkStrand() { this(""); }

    /**
     * Create a strand representing s. No error checking is done to see if s
     * represents valid genomic/DNA data.
     *
     * @param s is the source of cgat data for this strand
     */
    public LinkStrand(String s) { initialize(s); }

    /**
     * @return the total number of string characters in the LinkStrand
     */
    @Override
    public long size() { return mySize; }

    /**
     * Initialize this strand so that it represents the value of source. No
     * error checking is performed.
     *
     * @param source is the source of this enzyme
     */
    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        myCurrent = myFirst;
        myAppends = 0;
        myIndex = 0;
        myLocalIndex = 0;
        mySize = source.length();
    }

    /**
     * @return a LinkStrand object based on the string 'source'
     */
    @Override
    public IDnaStrand getInstance(String source) { return new LinkStrand(source); }

    /**
     * Append a strand of dna data to this strand. No error checking is
     * done.
     *
     * @param dna is the String appended to this strand
     */
    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        myAppends += 1;
        mySize += dna.length();
        return this;
    }

    /**
     * @return 'this' object in reverse (i.e. 'cgat' would become 'tagc')
     */
    @Override
    public IDnaStrand reverse() {

        Node rev = null;
        Node list = this.myFirst;
        while (list != null)
        {
            Node temp = list.next;
            rev = new Node(list.info, rev);
            list = temp;
        }
        Node current = rev;

        StringBuilder sb = new StringBuilder();
        while (current != null)
        {
            for (int i = current.info.length() - 1; i > -1; i--)
            {
                sb.append(current.info.charAt(i));
            }
            current = current.next;
        }
        LinkStrand ret = new LinkStrand();
        ret.append(sb.toString());
        return ret;
    }

    /**
     * @return the number of appends performed during the splicing process
     */
    @Override
    public int getAppendCount() { return myAppends; }

    /**
     * @return the DNA sequence of the object in string form (i.e. 'tgac')
     */
    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        Node current = this.myFirst;
        while (current != null && current.next != null)
        {
            str.append(current.info);
            current = current.next;
        }
        str.append(current.info);
        String ret = str.toString();
        return ret;
        //NOT TESTED
    }

    /**
     * @param index is the given index at which the method will search
     * @return the character at the given index
     */
    @Override
    public char charAt(int index) {
        if (0 > index || this.size() <= myIndex) { throw new IndexOutOfBoundsException(); }
        if (myIndex >= index) {
            myLocalIndex = 0;
            myIndex = 0;
            myCurrent = myFirst;
        }
        while (myIndex != index) {
            myLocalIndex += 1;
            myIndex += 1;
            if (myCurrent.next != null && myCurrent.info.length() <= myLocalIndex) {
                myLocalIndex = 0;
                myCurrent = myCurrent.next;

            }
        }
        return myCurrent.info.charAt(myLocalIndex);
    }
}
