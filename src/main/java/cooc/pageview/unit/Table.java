package cooc.pageview.unit;

import com.microsoft.playwright.Locator;
import cooc.pageview.PageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Table extends AbstractUnit{

    private static final String TABLE_CSS = "div.el-table:has( table)";
    private static final String TABLE_HEADER_CSS = "div[class*=\"el-table__header\"]";
    private static final String TABLE_BODY_CSS = "div[class*=\"el-table__body\"]";
    private static final String TABLE_ROW_CSS = "tr.el-table__row";
    private static final String HEADER_CELL_CSS = "th.el-table__cell";
    private static final String ROW_CELL_CSS = "td.el-table__cell";

    public Table(Locator unitRange, PageView pageView) {
        super(unitRange, TABLE_CSS, pageView);
    }


    public Locator getTableRowByHeader(String tableFlag,String header,String value){
        TableInfo tableInfo = getTableByFlag(tableFlag);
        return searchRow(tableInfo,header,value);
    }

    public Locator getTableRowByIndex(String tableFlag,int index){
        TableInfo tableInfo = getTableByFlag(tableFlag);
        return tableInfo.getCurrentPageRows().get(index);
    }

    public TableInfo getTableByFlag(String tableFlag){
        Locator table = getUnitByText(tableFlag);
        Locator header = table.locator(TABLE_HEADER_CSS);
        Locator body = table.locator(TABLE_BODY_CSS);
        return new TableInfo(table,header,body);
    }

    private Locator searchRow(TableInfo tableInfo,String header,String value){
        List<Locator> rows = tableInfo.getCurrentPageRows();
        if(!rows.isEmpty()){
           Optional<Locator> all = rows.stream().filter(row->tableInfo.checkRow(row,header,value)).findFirst();
           if(all.isPresent()){
               return all.get();
           }
        }
        return null;
    }

    public class TableInfo{
        private final Locator table;
        private final Locator header;
        private final Locator body;

        public TableInfo(Locator table, Locator header, Locator body) {
            this.table = table;
            this.header = header;
            this.body = body;
        }


        private List<Locator> getCurrentPageRows(){
            List<Locator> result = new ArrayList<>();
            if(body!=null){
                result = body.locator(TABLE_ROW_CSS).all();
            }
            return result;
        }

        private boolean checkRow(Locator row,String headerKey,String headerValue){
            if(header!=null && row!=null){
                int index = getHeaderIndex(header,headerKey);
                Locator row_cell = row.locator(ROW_CELL_CSS).all().get(index);
                return checkInnerText(row_cell,headerValue);
            }
            return false;
        }


        private int getHeaderIndex(Locator header,String headerKey){
            List<Locator> cells = header.locator(HEADER_CELL_CSS).all();
            if(!cells.isEmpty()){
                for (int i = 0; i < cells.size(); i++) {
                    boolean match = checkInnerText(cells.get(i),headerKey);
                    if(match){
                        return i;
                    }
                }
            }
            return -1;
        }


    }


}
