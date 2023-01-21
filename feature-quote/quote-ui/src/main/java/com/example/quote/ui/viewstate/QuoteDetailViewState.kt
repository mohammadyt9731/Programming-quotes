import com.example.base.ResultWrapper
import com.example.quote_model.AuthorWithQuotes

data class QuoteDetailViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize
)
