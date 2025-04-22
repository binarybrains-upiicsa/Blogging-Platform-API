type ErrorCode =
  | 'NOT_FOUND'
  | 'UNAUTHORIZED'
  | 'BAD_REQUEST'
  | 'SERVER_ERROR';

export type ApiSuccessResponse<T> = {
  success: true;
  data: T;
  message?: string;
};

export type ApiErrorResponse = {
  success: false;
  data: null;
  error: {
    message: string;
    code: ErrorCode;
  };
};

export type ApiResponse<T = null> = ApiSuccessResponse<T> | ApiErrorResponse;
