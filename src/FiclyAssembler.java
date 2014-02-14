public class FiclyAssembler {

    // "Bender's Key"
    // http://ficly.com/stories/37571
    
    // "System Check"
    // http://ficly.com/stories/36673

    public FiclyAssembler() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        //Series test = new Series("Test Series Please Ignore", FiclyUtils.getStoryWithURL("http://ficly.com/stories/36673"));
        Series test = new Series("Test Series Please Ignore", FiclyUtils.getStoryWithURL("http://ficly.com/stories/37571"));
        test.fetch();
        System.out.println(test.assembleString(Series.AssemblerType.depth_first));
    }

}
