package me.carc.overpasslib.query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.carc.overpasslib.output.OutputModificator;
import me.carc.overpasslib.output.OutputOrder;
import me.carc.overpasslib.output.OutputVerbosity;

import static me.carc.overpasslib.output.OutputFormat.JSON;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;


public class OverpassQueryTest {

    @Mock
    OverpassQueryBuilder builder;
    private OverpassQuery query;

    public OverpassQueryTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws Exception {
        query = new OverpassQuery(builder);
    }

    @Test
    public void testBuild() throws Exception {
        query.build();

        verify(builder).append(";");
    }

    @Test
    public void testFormat() throws Exception {
        query.format(JSON);

        verify(builder).clause("out", "json");
    }

    @Test
    public void testTimeout() throws Exception {
        query.timeout(30);

        verify(builder).clause("timeout", "30");
    }

    @Test
    public void testBoundingBox() throws Exception {
        query.boundingBox(1.00000001, 2.00000002, 3.00000003, 4.00000004);

        verify(builder).append("[bbox:1.00000001,2.00000002,3.00000003,4.00000004]");
    }

    @Test
    public void testFilterQuery() throws Exception {
        OverpassFilterQuery filterQuery = query.filterQuery();

        assertThat(filterQuery, isA(OverpassFilterQuery.class));
    }

    @Test
    public void testOutputWithDefaultArgs() throws Exception {
        query.output(100);

        verify(builder).append("; out body center qt 100");
    }

    @Test
    public void testOutputWithCustomArgs() throws Exception {
        query.output(OutputVerbosity.META, OutputModificator.GEOM, OutputOrder.ASC, 100);

        verify(builder).append("; out meta geom asc 100");
    }
}