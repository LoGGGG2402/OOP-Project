package ProcessData;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;

import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process {

    public static Map<String, String> extractCharacterInfo(String analysisResult) {
        Map<String, String> characterInfo = new HashMap<>();

        String[] lines = analysisResult.split("\\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\t");
            if (parts.length >= 5) {
                String entityType = parts[3];
                if (entityType.startsWith("B-PER")) {
                    String characterType = parts[4];
                    String characterName = parts[0];

                    if (characterInfo.containsKey(characterType)) {
                        String currentValue = characterInfo.get(characterType);
                        characterInfo.put(characterType, currentValue + ", " + characterName);
                    } else {
                        characterInfo.put(characterType, characterName);
                    }
                }
            }
        }

        return characterInfo;
    }




    public static void main(String[] args) {
        String analysisResult = "1\tLý_Thường\tN\tO\t13\tsub\n2\tKiệt1\tNp\tB-PER\t1\tnmod\n3\t(\tCH\tO\t1\tpunct\n4\tchữ\tN\tB-MISC\t1\tnmod\n5\tHán\tNp\tI-MISC\t4\tnmod\n6\t:\tCH\tO\t1\tpunct\n7\t李常傑\tM\tO\t1\tdet\n8\t;\tCH\tO\t1\tpunct\n9\t1019\tM\tO\t1\tdet\n10\t–\tCH\tO\t1\tpunct\n11\t1105\tM\tO\t1\tdet\n12\t)\tCH\tO\t1\tpunct\n13\tlà\tV\tO\t0\troot\n14\tnhà\tN\tO\t13\tdob\n15\tquân_sự\tN\tO\t14\tnmod\n16\t,\tCH\tO\t13\tpunct\n17\tnhà\tN\tO\t14\tnmod\n18\tchính_trị\tN\tO\t17\tnmod\n19\tthời\tN\tO\t14\tnmod\n20\tnhà\tN\tO\t19\tnmod\n21\tLý\tNp\tO\t20\tnmod\n22\tnước\tN\tO\t20\tnmod\n23\tĐại_Việt\tNp\tB-LOC\t22\tnmod\n24\t,\tCH\tO\t13\tpunct\n25\tlàm\tV\tO\t13\tvmod\n26\tquan\tN\tO\t25\tdob\n27\tqua\tV\tO\t25\tvmod\n28\t3\tM\tO\t29\tdet\n29\ttriều\tN\tO\t27\tvmod\n30\tvua\tN\tO\t29\tnmod\n31\tLý_Thái_Tông\tNp\tB-PER\t30\tnmod\n32\t,\tCH\tO\t30\tpunct\n33\tLý_Thánh_Tông\tNp\tB-PER\t30\tnmod\n34\tvà\tCc\tO\t29\tcoord\n35\tLý_Nhân\tNp\tB-PER\t34\tconj\n36\tTông\tNp\tI-PER\t29\tnmod\n37\t.\tCH\tO\t13\tpunct";

        Map<String, String> characterInfo = extractCharacterInfo(analysisResult);

        for (Map.Entry<String, String> entry : characterInfo.entrySet()) {
            String characterType = entry.getKey();
            String characterNames = entry.getValue();
            System.out.println("<\"" + characterType + "\" : \"" + characterNames + "\">");
        }
    }
}
