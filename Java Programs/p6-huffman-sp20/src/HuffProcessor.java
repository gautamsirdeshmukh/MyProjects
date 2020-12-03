import java.util.PriorityQueue;

/**
 * Spring 2020
 * Although this class has a history of several years,
 * it is starting from a blank-slate, new and clean implementation
 * as of Fall 2018.
 * <P>
 * Changes include relying solely on a tree for header information
 * and including debug and bits read/written information
 * 
 * @author Owen Astrachan
 */

public class HuffProcessor {

	public static final int BITS_PER_WORD = 8;
	public static final int BITS_PER_INT = 32;
	public static final int ALPH_SIZE = (1 << BITS_PER_WORD); 
	public static final int PSEUDO_EOF = ALPH_SIZE;
	public static final int HUFF_NUMBER = 0xface8200;
	public static final int HUFF_TREE  = HUFF_NUMBER | 1;

	private final int myDebugLevel;
	
	public static final int DEBUG_HIGH = 4;
	public static final int DEBUG_LOW = 1;
	
	public HuffProcessor() {
		this(0);
	}
	
	public HuffProcessor(int debug) {
		myDebugLevel = debug;
	}

	/**
	 * Compresses a file. Process must be reversible and loss-less.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be compressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void compress(BitInputStream in, BitOutputStream out)
	{
		int[] counts = new int[1 + ALPH_SIZE];
		int bits = in.readBits(BITS_PER_WORD);

		while (bits != -1)
		{
			counts[bits]++;
			bits = in.readBits(BITS_PER_WORD);
		}

		counts[PSEUDO_EOF] = 1;
		PriorityQueue<HuffNode> pq = new PriorityQueue<>();

		for (int i = 0; i < counts.length; i++)
		{
			if (counts[i] > 0)
				pq.add(new HuffNode(i, counts[i], null, null));
		}

		while (pq.size() > 1)
		{
			HuffNode l = pq.remove(), r = pq.remove();

			HuffNode newTree = new HuffNode(0, l.myWeight + r.myWeight, l, r);
			pq.add(newTree);
		}

		HuffNode node = pq.remove();
		String[] codings = new String[1 + ALPH_SIZE];
		makeCodingsFromTree(node, codings, "");

		out.writeBits(BITS_PER_INT, HUFF_TREE);
		writeHeader(node, out);
		in.reset();

		while (true)
		{
			int newbits = in.readBits(BITS_PER_WORD);
			if (newbits == -1) break;

			String code = codings[newbits];
			if (code != null)
				out.writeBits(code.length(), Integer.parseInt(code, 2));
		}

		String pseudo = codings[PSEUDO_EOF];
		out.writeBits(pseudo.length(), Integer.parseInt(pseudo, 2));
		out.close();
	}

	private void writeHeader(HuffNode node, BitOutputStream out)
	{
		if (node.myRight != null || node.myLeft != null) {
			out.writeBits(1, 0);

			writeHeader(node.myLeft, out);
			writeHeader(node.myRight, out);
		}
		else {
			out.writeBits(1, 1);
			out.writeBits(1 + BITS_PER_WORD, node.myValue);
		}
	}

	private void makeCodingsFromTree(HuffNode node, String[] encodings, String sample)
	{
		if (node.myRight == null && node.myLeft == null)
		{
			encodings[node.myValue] = sample;
			return;
		}

		makeCodingsFromTree(node.myLeft, encodings, sample + "0");
		makeCodingsFromTree(node.myRight, encodings, sample + "1");
	}

	/**
	 * Decompresses a file. Output file must be identical bit-by-bit to the
	 * original.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be decompressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void decompress(BitInputStream in, BitOutputStream out)
	{
		int bits = in.readBits(BITS_PER_INT);

		if (bits == -1) throw new HuffException("illegal header starts with " + bits);
		if (bits != HUFF_TREE) throw new HuffException("illegal header starts with " + bits);

		HuffNode root = readTree(in), current = root;

		while (true)
		{
			int newbits = in.readBits(1);

			if (newbits == -1) throw new HuffException("reading bits has failed");
			else {
				if (newbits == 0) current = current.myLeft;

				else current = current.myRight;
				if (current.myRight == null && current.myLeft == null)
				{
					if (current.myValue == PSEUDO_EOF) break;
					else {
						out.writeBits(BITS_PER_WORD, current.myValue);
						current = root;
					}
				}
			}
		}
		out.close();
	}
	private HuffNode readTree(BitInputStream in)
	{
		int bits = in.readBits(1);

		if (bits == -1) throw new HuffException("reading bits has failed");
		if (bits == 0) {
			HuffNode l = readTree(in), r = readTree(in);
			return new HuffNode(0, 0, l, r);
		}
		else
			return new HuffNode((in.readBits(1 + BITS_PER_WORD)), 0, null, null);
	}
}