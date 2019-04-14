(
s.waitForBoot({
    var m1intro = MeloMorph.new("a4_8 b c5");
    var m1 = MeloMorph();
    var m2intro = MeloMorph.new("r_8 r r");
    var m2 = MeloMorph();
    m1.set_melody(\source, "f5_8*5/1 g4_8 a b e5_8*5/1 f4_8 g a d5_8*5/1 b4_8 d5_8 c5_8 a4_8*5/1 a4_8 b c5");
    m2.set_melody(\source, "d3_8 e f f# g f e d c c# d e f c a2 f b c3 d f e d c b2 a b c3 d e f g g#");
    /*
    Pseq([
        Ppar([
            m1intro.panola_melody[\source].asPbind,
            m2intro.panola_melody[\source].asPbind],
        1),
        Ppar([
            m1.panola_melody[\source].asPbind,
            m2.panola_melody[\source].asPbind],
        2)],
    ).play;
    */
});
)

