declare module '@apiverve/lexicon' {
  export interface lexiconOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface lexiconResponse {
    status: string;
    error: string | null;
    data: WordLexiconAnalyzerData;
    code?: number;
  }


  interface WordLexiconAnalyzerData {
      word:                   string;
      isAlphagram:            boolean;
      sortedForm:             string;
      isPalindrome:           boolean;
      hasAnagram:             boolean;
      isIsogram:              boolean;
      isPangramCandidate:     boolean;
      isScrabbleValid:        boolean;
      canBePalindromeAnagram: boolean;
  }

  export default class lexiconWrapper {
    constructor(options: lexiconOptions);

    execute(callback: (error: any, data: lexiconResponse | null) => void): Promise<lexiconResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: lexiconResponse | null) => void): Promise<lexiconResponse>;
    execute(query?: Record<string, any>): Promise<lexiconResponse>;
  }
}
