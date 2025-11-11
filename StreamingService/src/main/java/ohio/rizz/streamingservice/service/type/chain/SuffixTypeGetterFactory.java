package ohio.rizz.streamingservice.service.type.chain;

import ohio.rizz.streamingservice.service.type.chain.internal.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuffixTypeGetterFactory {
    List<SuffixTypeGetterHandler> handlers = List.of(new MpegSuffixTypeGetterHandler(),
                                                             new M4aSuffixTypeGetterHandler(),
                                                             new Mp4SuffixTypeGetterHandler(),
                                                             new OggSuffixTypeGetterHandler(),
                                                             new AacSuffixTypeGetterHandler(),
                                                             new WavSuffixTypeGetterHandler(),
                                                             new WmaSuffixTypeGetterHandler());
    @Bean
    public SuffixTypeGetterHandler getSuffixTypeChain() {
        SuffixTypeGetterHandler chainBeginning = new FlacSuffixTypeGetterHandler();
        SuffixTypeGetterHandler tmpHandler = chainBeginning;
        for (var handler : handlers) {
            tmpHandler = tmpHandler.setNext(handler);
        }

        return chainBeginning;
    }
}
