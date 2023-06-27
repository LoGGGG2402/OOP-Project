package ProcessData;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;

import java.io.IOException;

public class Process {




    public static void main(String[] args) {
        String[] annotators = {"wseg", "pos", "ner", "parse"};
        try {
            VnCoreNLP pipeline = new VnCoreNLP(annotators);
            String str = "Chiến dịch Hồ Chí Minh, tên ban đầu là Chiến dịch Giải phóng Sài Gòn – Gia Định là chiến dịch cuối cùng của Quân Giải phóng miền Nam Việt Nam trong Cuộc Tổng tấn công và nổi dậy mùa Xuân năm 1975 và Chiến tranh Việt Nam. Đây cũng là chiến dịch quân sự diễn ra trong thời gian ngắn nhất trong Chiến tranh Việt Nam, diễn ra từ ngày 26 tháng 4 đến ngày 30 tháng 4 năm 1975 tại Sài Gòn và kéo theo là sự tiếp quản của chính phủ Mặt trận Dân tộc Giải phóng miền Nam Việt Nam và Chính phủ Cách mạng lâm thời Cộng hòa miền Nam Việt Nam tại Đồng bằng sông Cửu Long trong hai ngày 1 và 2 tháng 5. Chiến dịch này dẫn đến việc chấm dứt hoàn toàn sự chia cắt thành hai vùng tập kết quân sự về mặt lãnh thổ giữa hai miền Nam – Bắc của Việt Nam vào năm 1975, đưa đến việc thống nhất xã hội, chế độ chính trị, dân cư và toàn vẹn lãnh thổ của Việt Nam trên đất liền, vùng lãnh hải, vùng trời và một số hải đảo [10] khác của Việt Nam vào năm 1976.";
            Annotation annotation = new Annotation(str);
            pipeline.annotate(annotation);
            System.out.println(annotation);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
