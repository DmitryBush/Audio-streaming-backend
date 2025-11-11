package ohio.rizz.streamingservice.service.type.chain.factory;

import ohio.rizz.streamingservice.service.type.chain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SuffixTypeGetterFactory {
    List<AbstractSuffixTypeGetterHandler> handlers = List.of(new MpegSuffixTypeGetterHandler(),
                                                             new M4aSuffixTypeGetterHandler(),
                                                             new Mp4SuffixTypeGetterHandler(),
                                                             new OggSuffixTypeGetterHandler(),
                                                             new AacSuffixTypeGetterHandler(),
                                                             new WavSuffixTypeGetterHandler(),
                                                             new WmaSuffixTypeGetterHandler());
    @Bean
    public AbstractSuffixTypeGetterHandler getSuffixTypeChain() {
        AbstractSuffixTypeGetterHandler chainBeginning = new FlacSuffixTypeGetterHandler();
        AbstractSuffixTypeGetterHandler tmpHandler = chainBeginning;
        for (var handler : handlers) {
            tmpHandler = tmpHandler.setNext(handler);
        }

        return chainBeginning;
    }
}
