export interface UserProfile{
  username?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  token?: string;
  roles?: string[] | undefined;
}
