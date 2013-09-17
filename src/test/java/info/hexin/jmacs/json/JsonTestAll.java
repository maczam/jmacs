package info.hexin.jmacs.json;

import info.hexin.jmacs.json.parser.ParserTestAll;
import info.hexin.jmacs.json.serialize.JsonSerializeTestAll;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JsonSerializeTestAll.class, ParserTestAll.class })
public class JsonTestAll {

}
