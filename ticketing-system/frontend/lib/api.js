export const API_ROOT = process.env.NEXT_PUBLIC_API_ROOT || 'http://localhost:8080/api';

export async function api(path, token, opts){
  const headers = opts && opts.headers ? opts.headers : {};
  if(token) headers['Authorization'] = 'Bearer ' + token;
  const res = await fetch(API_ROOT + path, {...opts, headers});
  return res.json();
}
