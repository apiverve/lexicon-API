declare module '@apiverve/lexicon' {
  export interface lexiconOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface lexiconResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class lexiconWrapper {
    constructor(options: lexiconOptions);

    execute(callback: (error: any, data: lexiconResponse | null) => void): Promise<lexiconResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: lexiconResponse | null) => void): Promise<lexiconResponse>;
    execute(query?: Record<string, any>): Promise<lexiconResponse>;
  }
}
