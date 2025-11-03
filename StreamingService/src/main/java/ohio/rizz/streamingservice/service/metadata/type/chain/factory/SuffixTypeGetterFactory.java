package ohio.rizz.streamingservice.service.metadata.type.chain.factory;

import ohio.rizz.streamingservice.service.metadata.type.chain.AbstractSuffixTypeGetterHandler;
import ohio.rizz.streamingservice.service.metadata.type.chain.FlacSuffixTypeGetterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class SuffixTypeGetterFactory {
    @Bean
    public AbstractSuffixTypeGetterHandler getSuffixTypeChain() {
        AbstractSuffixTypeGetterHandler handler = new FlacSuffixTypeGetterHandler();
        return handler;
    }
}
