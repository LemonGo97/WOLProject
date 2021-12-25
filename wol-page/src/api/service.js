import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/service',
    method: 'get',
    params
  })
}

export function getOne(id) {
  return request({
    url: '/service/' + id,
    method: 'get'
  })
}

export function save(data) {
  return request({
    url: '/service',
    method: 'post',
    data
  })
}

export function remove(id) {
  return request({
    url: '/service/' + id,
    method: 'delete'
  })
}

export function modify(data) {
  return request({
    url: '/service',
    method: 'put',
    data
  })
}
