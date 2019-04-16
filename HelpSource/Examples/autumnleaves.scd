(
s.waitForBoot({
    var m1intro = MeloMorph.new("a4_8 b c5");
    var m1 = MeloMorph();
    var m2intro = MeloMorph.new("r_8 r r");
    var m2 = MeloMorph();
    var patterns;
    var repeats = 5;

    m1.set_melody(\source, "f5_8*5/1 g4_8 a b e5_8*5/1 f4_8 g a d5_8*5/1 b4_8 d5_8 c5_8 a4_8*5/1 a4_8 b c5");
    m2.set_melody(\source, "d3_8 e f f# g f e d c c# d e f c a2 f b c3 d f e d c b2 a b c3 d e f g g#");
    m1.set_melody(\target, "c5_8. a4_16 b4_8. a4_16 c5_8. a4_16 b4_4 b4_8. g4_16 a4_8. g4_16 b4_8. g4_16 a4_4 c5_8. a4_16 b4_8. a4_16 c5_8. a4_16 b4_8 g4_8 c5_2");
    m2.set_melody(\target, "d3_4 g d_16 e f g_16*5 c_4 g c_16 e f g_16*5 d_4 g d g c_2");

    [m1, m2].do({
        |m|
        [\source, \target].do({
           |type|
            m.create_iteration_plan(type, repeats);
            m.decorate_notes(type);
        });
    });

    patterns = [
        Pseq([
            Ppar([
                m1intro.panola_melody[\source].asPbind,
                m2intro.panola_melody[\source].asPbind],
            1),
            Ppar([
                m1.panola_melody[\source].asPbind,
                m2.panola_melody[\source].asPbind],
            1)],
        )
    ];

    patterns = patterns ++ repeats.collect({
        |step|
        m1.shuffle(\source, step);
        m2.shuffle(\source, step);
        Ppar([
            m1.undecorate_asPbind(\source, \default),
            m2.undecorate_asPbind(\source, \default)
        ],1);
    });

    m1.init_substitutions(\target, \source);
    patterns = patterns ++ repeats.collect({
        |step|
        m1.substitute(\target, \source, step);
        m2.substitute(\target, \source, step);
        Ppar([
            m1.undecorate_asPbind(\source, \default),
            m2.undecorate_asPbind(\source, \default)
        ],1);
    });

    [m1, m2].do({
        |m|
        m.create_iteration_plan(\source, repeats.div(2))
    });

    patterns = patterns ++ repeats.div(2).collect({
        |step|
        m1.bubblesort(\source, step);
        m2.bubblesort(\source, step);
        Ppar([
            m1.undecorate_asPbind(\source, \default),
            m2.undecorate_asPbind(\source, \default)
        ],1);
    });

    Pseq(patterns, 1).play(TempoClock(0.5));
});
)

